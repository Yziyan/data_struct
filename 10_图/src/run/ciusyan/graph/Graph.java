package run.ciusyan.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 图的接口
 * @param <V>：顶点存储的元素类型
 * @param <E>：边存储的权值类型
 */
public abstract class Graph<V, E> {

    protected WeightManager<E> weightManager;

    public Graph() {}

    public Graph(WeightManager<E> weightManager) {
        this.weightManager = weightManager;
    }

    /**
     * 顶点的数量
     */
    public abstract int verticesSize();

    /**
     * 边的数量
     */
    public abstract int edgeSize();

    /**
     * 添加一个顶点
     * @param v：顶点存储的元素
     */
    public abstract void addVertex(V v);

    /**
     * 删除顶点
     */
    public abstract void removeVertex(V v);

    /**
     * 添加边，无权值
     */
    public abstract void addEdge(V fromV, V toV);

    /**
     * 添加边
     * @param fromV：从哪个顶点出发
     * @param toV：到哪个顶点
     * @param weight：权值
     */
    public abstract void addEdge(V fromV, V toV, E weight);

    /**
     * 删除边
     * @param fromV：从哪个顶点出发
     * @param toV：到哪个顶点
     */
    public abstract void removeEdge(V fromV, V toV);

    /**
     * 广度优先遍历
     * @param begin：起点
     * @param visitor：访问器
     */
    public abstract void bfs(V begin, VertexVisitor<V> visitor);

    /**
     * 深度优先遍历
     * @param begin：起点
     * @param visitor：访问器
     */
    public abstract void dfs(V begin, VertexVisitor<V> visitor);

    /**
     * 拓扑排序
     * @return ：返回排序后的集合
     */
    public abstract List<V> topological();

    /**
     * 获取一棵最小生成树
     * @return ：最小生成树包含的边
     */
    public abstract Set<EdgeInfo<V, E>> mst();

    /**
     * 求从 src 开始的源点，到其他点的最短路径
     * @param src：源点
     * @return <"B", 10>，<"C", 30> ...
     */
    public abstract Map<V, E> shortPath(V src);

    /**
     * 非递归使用这个 访问器
     */
    public interface VertexVisitor<V> {
        boolean visit(V v);
    }

    /**
     * 递归时，使用这个访问器
     */
    public abstract class Visitor<V> {
        // 用于记录是否需要停止递归
        boolean stop;

        protected abstract boolean visit(V v);
    }

    /**
     * 边的信息，mst 时使用
     */
    public static class EdgeInfo<V, E> {
        // 起点
        V from;
        // 终点
        V to;
        // 权值
        E weight;

        public EdgeInfo(V from, V to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EdgeInfo{" +
                "from=" + from +
                ", to=" + to +
                ", weight=" + weight +
                '}';
        }
    }

    public interface WeightManager<E> {
        /**
         * 比较权值大小
         */
        int compare(E w1, E w2);

        /**
         * 计算 w1 + w2
         */
        E add(E w1, E w2);
        // ...
    }
}
