package run.ciusyan.bf;

/**
 * 布隆过滤器
 */
public class BloomFilter<V> {

    /**
     * 二进制向量的长度（一共有多少个二进制位）
     */
    private int bitSize;

    /**
     * 二进制向量，使用long，因为一个long可以放 8 个字节，
     * 可存放 64 位的数据
     */
    private long[] bits;

    /**
     * 哈希函数的数量
     */
    private int hashSize;

    /**
     * @param n 数据规模
     * @param p 误判率
     */
    public BloomFilter(int n, double p) {
        if (n <= 0 || p <= 0 || p >= 1) {
            throw new IllegalArgumentException("请正确的传递参数");
        }

        double ln2 = Math.log(2);

        // 二进制向量的长度
        bitSize = (int) (- (n * Math.log(p)) / (ln2 * ln2));
        // 哈希函数的数量
        hashSize = (int) (bitSize * ln2 / n);

        // 初始化二进制向量
        // 和计算分页数差不多
        //  bitSize = 65 -> 65 + 64 - 1 / 64 = 2
        bits = new long[(bitSize + Long.SIZE - 1) / Long.SIZE];
    }

    /**
     * 添加元素
     */
    public void put(V value) {
        valueCheck(value);

        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        // 有多少哈希函数，就计算出多少个索引
        for (int i = 1; i <= hashSize; i++) {
            int combineHash = (i * hash2) + hash1;
            if (combineHash < 0) {
                // 不能为负数
                combineHash = ~combineHash;
            }

            // 计算出对应哈希的索引
            int index = combineHash % bitSize;

            // 将二进制向量中，对应索引位置的值设置为 1
            set(index);
        }
    }

    /**
     * 查看元素是否存在
     */
    public boolean contains(V value) {
        valueCheck(value);

        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        // 有多少哈希函数，就计算出多少个索引
        for (int i = 1; i <= hashSize; i++) {
            int combineHash = (i * hash2) + hash1;
            if (combineHash < 0) {
                combineHash = ~combineHash;
            }

            // 计算出对应哈希的索引
            int index = combineHash % bitSize;
            // 判断对应二进制数组中对应的索引位置，值是否不为 1
            // 如果此value的hash函数计算出来的索引位置有一个不为 1，说明一定不存在此 value

            if (!get(index)) return false;
        }

        // 能来到这里，说明存在了
        return true;
    }

    /**
     * 设置二进制数组中，index 位置的值为 1
     */
    private void set(int index) {
        // 二进制向量，属于哪一个long：index / Long.SIZE
        //      比如：index = 130 -> 130 / 64 = 2 -> 那么也就是俗语 bits[2] 这个区间
        long value = bits[index / Long.SIZE];

        // 将二进制的某一位设置为 1，只需要将其按位或 | 即可
        // 将 0010 第三位，设置为 1
        //  如：   0010
        //       | 0100
        //      -> 0110

        // 那么 0100 怎么来呢？我们可以将其每一个long区间，按照，从右到左来标识第几位
        // 那么bits数组，也就是下面这种结构
        // 第一个long区间             第二个long区间
        // [63 62 ... 0 0 3 2 1 0] [127 126 ... 0 0 0 66 65 64]
        // 所以，index % Long.SIZE，就是在每一个区间的第几个位置
        // 比如 130 -> 131 % 64 = 3，说明是 第二个区间，的3号位置
        // 那么，也就可以将 1 << 3，得到只有 3 号位置是 1 的二进制数
        // 也就可以写了

        bits[index / Long.SIZE] = value | (1 << (index % Long.SIZE));
    }

    /**
     * 判断二进制数组中，index 位置的值是否为 1
     * @return 1：true 0：false
     */
    private boolean get(int index) {
        // 与上面的分析类似，只不过需要判断某一位是不是 0 ，可以通过 按位与 & 运算即可
        long value = bits[index / Long.SIZE];

        // 如此操作，判断 0101 第二位是否为 0
        //      0101
        //    & 0010
        // ->   0000 == 0 -> 说明第二位是 0
        return (value & (1 << (index % Long.SIZE))) != 0;
    }

    private void valueCheck(V value) {
        if (value == null) {
            throw new IllegalArgumentException("value 不能为 null");
        }
    }
}
