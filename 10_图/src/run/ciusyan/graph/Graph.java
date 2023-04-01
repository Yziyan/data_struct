package run.ciusyan.graph;

import java.util.List;

/**
 * 图的接口
 * @param <V>：顶点存储的元素类型
 * @param <E>：边存储的权值类型
 */
public interface Graph<V, E> {
    /**
     * 顶点的数量
     */
    int verticesSize();

    /**
     * 边的数量
     */
    int edgeSize();

    /**
     * 添加一个顶点
     * @param v：顶点存储的元素
     */
    void addVertex(V v);

    /**
     * 删除顶点
     */
    void removeVertex(V v);

    /**
     * 添加边，无权值
     */
    void addEdge(V fromV, V toV);

    /**
     * 添加边
     * @param fromV：从哪个顶点出发
     * @param toV：到哪个顶点
     * @param weight：权值
     */
    void addEdge(V fromV, V toV, E weight);

    /**
     * 删除边
     * @param fromV：从哪个顶点出发
     * @param toV：到哪个顶点
     */
    void removeEdge(V fromV, V toV);

    /**
     * 广度优先遍历
     * @param begin：起点
     * @param visitor：访问器
     */
    void bfs(V begin, VertexVisitor<V> visitor);

    /**
     * 深度优先遍历
     * @param begin：起点
     * @param visitor：访问器
     */
    void dfs(V begin, VertexVisitor<V> visitor);

    /**
     * 拓扑排序
     * @return ：返回排序后的集合
     */
    List<V> topological();

    /**
     * 非递归使用这个 访问器
     */
    interface VertexVisitor<V> {
        boolean visit(V v);
    }

    /**
     * 递归时，使用这个访问器
     */
    abstract class Visitor<V> {
        // 用于记录是否需要停止递归
        boolean stop;

        protected abstract boolean visit(V v);
    }
}
