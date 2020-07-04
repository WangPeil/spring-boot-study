package com.example.bloom;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;

/**
 * @Author: peili.wang
 * @Date: 2020/4/10 17:27
 */
public class BloomFilterHelper<T> {
    private int numHashFunctions;
    private int bitSize;

    private Funnel<T> funnel;

    public BloomFilterHelper(int expectedInsertions, double fpp, Funnel<T> funnel) {
        Preconditions.checkArgument(funnel!=null,"funnel不能为空");
        this.funnel = funnel;
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    int[] murmurHashOffset(T value) {
        int[] offset = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i < numHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }
        return offset;
    }

    private int optimalNumOfBits(long expectedInsertions, double fpp) {
        if (fpp == 0) {
            fpp = Double.MIN_VALUE;
        }
        return (int) (-expectedInsertions * Math.log(fpp) / (Math.log(2) * Math.log(2)));
    }

    private int optimalNumOfHashFunctions(long expectedInsertions, long size) {
        return Math.max(1, (int) Math.round((double) expectedInsertions / size * Math.log(2)));
    }
}
