package run.ciusyan.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 0-1背包问题
 */
public class Article {

    // 重量
    private int weight;
    // 价值
    private int value;
    // 性价比
    private double valueDensity;

    public Article() {}

    public Article(int weight, int value) {
        this.weight = weight;
        this.value = value;
        this.valueDensity = value * 1.0 / weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public double getValueDensity() {
        return valueDensity;
    }

    // 背起背包
    public void backKnapsack(String title, Comparator<Article> comparator) {
        Article[] articles = new Article[] {
            new Article(35, 10), new Article(30, 40),
            new Article(60, 30), new Article(50, 50),
            new Article(40, 35), new Article(10, 40),
            new Article(25, 30)
        };

        // 将背包排序
        Arrays.sort(articles, comparator);

        // 背包容量、已装重量、已选价值
        int capacity = 150, weight = 0, value = 0;

        // 已选择的物品
        List<Article> selected = new LinkedList<>();

        for (Article article : articles) {
            if (weight >= capacity) return;

            int newWeight = weight + article.weight;
            if (newWeight > capacity) continue;

            // 来到这里说明可以选择此件物品
            value += article.value;
            weight = newWeight;
            selected.add(article);
        }

        System.out.println("【" + title + "】");
        System.out.println("总价值：" + value);
        for (Article article : selected) {
            System.out.println(article);
        }
        System.out.println("-----------------------------");
    }

    @Override
    public String toString() {
        return "Article{" +
            "weight=" + weight +
            ", value=" + value +
            ", valueDensity=" + valueDensity +
            '}';
    }
}
