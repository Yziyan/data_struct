package run.ciusyan.match;

/**
 * 蛮力算法
 */
public class BruteForce {

    /**
     * 蛮力匹配 3
     */
    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tlen = textChars.length;
        if (tlen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int plen = patternChars.length;
        if (plen == 0) return -1;
        if (plen > tlen) return -1;

        int delta = tlen - plen;
        // 每一次 for 循环，就是一轮比较
        for (int ti = 0; ti <= delta; ti++) {

            // 每一轮比较，都需要从模式串的 0 号位置开始比较
            int pi = 0;
            // 挨个将模式串 pi 位置进行比较
            while (pi < plen) {
                // 说明遇到一个不匹配的，直接跳过后面的比较，进入下一轮比较
                if (textChars[ti + pi] != patternChars[pi]) break;

                // 能来到这里，说明 pi + 1，需要比较此轮的下一个字符
                pi++;
            }

            // 如果到这里来了，可能是不匹配，需要进行下一轮比较
            //  但是也可能是，pi = plen ，正常的将所有字符都匹配上了，那么直接此轮就找出了索引位置
            if (pi == plen) return ti;
        }

        // 来到这里，说明文本串中找不到匹配的模式串
        return -1;
    }

    /**
     * 暴力匹配 2 —— 在恰当的位置，可以提前退出比较
     */
    public static int indexOf2(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tlen = textChars.length;
        if (tlen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int plen = patternChars.length;
        if (plen == 0) return -1;
        if (plen > tlen) return -1;

        int ti = 0, pi = 0, delta = tlen - plen;
        while (pi < plen && ti - pi <= delta) {
            if (textChars[ti] == patternChars[pi]) {
                ti++;
                pi++;
            } else {
                ti -= pi - 1;
                pi = 0;
            }
        }

        return (pi == plen) ? ti - pi : -1;
    }

    /**
     * 暴力匹配 1，
     */
    public static int indexOf1(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tlen = textChars.length;
        if (tlen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int plen = patternChars.length;
        if (plen == 0) return -1;
        if (plen > tlen) return -1;

        // 用于标识正在匹配的索引
        int ti = 0, pi = 0;
        while (pi < plen && ti < tlen) {
            if (textChars[ti] == patternChars[pi]) {
                // 说明此字符匹配，继续匹配下一个
                ti++;
                pi++;
            } else {
                // 说明此字符不匹配，需要往后挪动模式串
                ti -= pi - 1;
                pi = 0;
            }
        }

        // 能来到这里，说明 pi == plen or ti == tlen
        //  pi == plen：说明模式串全部匹配成功了，返回 ti - pi，这一次匹配的起始位置
        //  ti == tlen：说明文本串越界了，模式串还没匹配完毕，说明找不到此模式串
        return (pi == plen) ? ti - pi : -1;
    }

}
