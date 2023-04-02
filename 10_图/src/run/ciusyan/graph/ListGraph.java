package run.ciusyan.graph;

import java.util.*;

/**
 * 偏向邻接表的实现
 */
public class ListGraph<V, E> implements Graph<V, E> {

    /**
     * 存储图中所有的顶点
     */
    private Map<V, Vertex<V, E>> vertices;
    /**
     * 存储图中所有的边
     */
    private Set<Edge<V, E>> edges;

    /**
     * 边的比较器
     */
    private Comparator<Edge<V, E>> edgeComparator;

    public ListGraph() {
        vertices = new HashMap<>();
        edges = new HashSet<>();

        edgeComparator = (e1, e2) -> {

            return 0;
        };
    }

    /**
     * 用于简单测试结果
     */
    public void print() {
        System.out.println("[顶点]-------------------");
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            System.out.println(v);
            System.out.println("out-----------");
            System.out.println(vertex.outEdges);
            System.out.println("in-----------");
            System.out.println(vertex.inEdges);
        });

        System.out.println("[边]-------------------");
        edges.forEach((Edge<V, E> edge) -> {
            System.out.println(edge);
        });
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public void addVertex(V v) {
        if (vertices.containsKey(v)) return;

        vertices.put(v, new Vertex<>(v));
    }

    @Override
    public void removeVertex(V v) {
        // 直接尝试删除顶点，如果删除成功，会返回被删除的顶点
        Vertex<V, E> vertex = vertices.remove(v);
        if (vertex == null) return;

        // 来到这里，已经将顶点删除了

        // 通过迭代器删除 inEdges （说明这里是终点）
        for (Iterator<Edge<V, E>> iterator = vertex.inEdges.iterator(); iterator.hasNext();) {
            Edge<V, E> edge = iterator.next();
            edge.from.outEdges.remove(edge); // 去删除起点中，对应出去的边
            // 如果要在迭代的时候删除自己，去删除 vertex.inEdges 中的这条边
            iterator.remove();
            edges.remove(edge); // 总边集合中删除这条边
        }

        // 通过迭代器删除 outEdges （说明这里是起点）
        for (Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator(); iterator.hasNext();) {
            Edge<V, E> edge = iterator.next();
            edge.to.inEdges.remove(edge); // 去删除终点中，对应进来的边
            // 如果要在迭代的时候删除自己，去删除 vertex.inEdges 中的这条边
            iterator.remove();
            edges.remove(edge); // 总边集合中删除这条边
        }
    }

    @Override
    public void addEdge(V fromV, V toV) {
        addEdge(fromV, toV, null);
    }

    @Override
    public void addEdge(V fromV, V toV, E weight) {
        Vertex<V, E> fromVertex = vertices.get(fromV);
        if (fromVertex == null) {
            // 说明还没有此顶点，创建，并且放入 map 中
            fromVertex = new Vertex<>(fromV);
            vertices.put(fromV, fromVertex);
        }
        Vertex<V, E> toVertex = vertices.get(toV);
        if (toVertex == null) {
            // 说明还没有此顶点，创建，并且放入 map 中
            toVertex = new Vertex<>(toV);
            vertices.put(toV, toVertex);
        }

        // 来到这里，和这条边相关的顶点，肯定已经有了
        // 我们得先判断这条边，以前是不是存在，因为可能存在了，只是来修改这条边的权值的
        // 那么该如和判断该边是否存在呢？ 这就得根据这两个顶点中的 Set<Edge<V, E>> inEdges 来查看了
        // 也就是：fromVertex.outEdges.contains(边); 代表起点，有哪些出边
        // 当然，也可以：toVertex.inEdges.contains(边); 代表终点，有哪些入边

        // 直接新建一条边
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        edge.weight = weight;
        if (fromVertex.outEdges.remove(edge)) { // 说明以前有这一条边，

            // 去删除另一顶点中对应的边
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }

        // 不管以前有没有，到这里肯定都没有了，添加进去
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void removeEdge(V fromV, V toV) {
        Vertex<V, E> fromVertex = vertices.get(fromV);
        if (fromVertex == null) return;

        Vertex<V, E> toVertex = vertices.get(toV);
        if (toVertex == null) return;

        // 来到这里，顶点肯定是存在的，尝试去删除边
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);

        // 尝试去删除这条边
        if (edges.remove(edge)) { // 说明这条边存在
            // 去起点中出边集合 删除这条边
            fromVertex.outEdges.remove(edge);
            // 去终点中入边集合 删除这条边
            toVertex.inEdges.remove(edge);
        }
    }

    /**
     * 顶点
     */
    private static class Vertex<V, E> {
        /**
         * 顶点存储的元素
         */
        V value;
        /**
         * 指向自己的边
         */
        Set<Edge<V, E>> inEdges = new HashSet<>();
        /**
         * 从自己这里指出的边
         */
        Set<Edge<V, E>> outEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Vertex<V, E> vertex = (Vertex<V, E>) obj;

            return Objects.equals(this.value, vertex.value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    /**
     * 边
     */
    private static class Edge<V, E> {
        /**
         * 边的起点
         */
        Vertex<V, E> from;
        /**
         * 边的终点
         */
        Vertex<V, E> to;
        /**
         * 边的权值
         */
        E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Edge<V, E> edge = (Edge<V, E>) obj;

            return Objects.equals(this.from, edge.from) && Objects.equals(this.to, edge.to);
        }

        @Override
        public int hashCode() {
            // return from.hashCode() * 31 +  to.hashCode();
            return (from == null ? 0 : from.hashCode() * 31) +  (to == null ? 0 : to.hashCode());
        }

        @Override
        public String toString() {
            return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
        }
    }

    @Override
    public void bfs(V begin, VertexVisitor<V> visitor) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        // 准备一个集合，用于记录已经遍历过的节点
        Set<Vertex<V, E>> visitedVertices = new HashSet<>();

        // 准备一个队列
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        // 将起点入队
        queue.offer(beginVertex);
        visitedVertices.add(beginVertex);

        // 队列不为空，就一直遍历
        while (!queue.isEmpty()) {

            // 访问对头元素
            Vertex<V, E> vertex = queue.poll();
            if(visitor.visit(vertex.value)) return;

            // 根据起点，找到它的出边
            for (Edge<V, E> edge : vertex.outEdges) {
                // 根据相连的边，找到终点，将其入队，但是需要判断是否已经遍历过了
                if (visitedVertices.contains(edge.to)) continue;

                queue.offer(edge.to);
                visitedVertices.add(edge.to);
            }
        }
    }


    /**
     * 非递归实现 1
     */
    public void dfs1(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;

        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        // 准备一个集合，用于记录已经遍历过的节点
        Set<Vertex<V, E>> visitedVertices = new HashSet<>();
        // 准备一个栈
        Stack<Vertex<V, E>> stack = new Stack<>();
        stack.push(beginVertex);

        while (!stack.isEmpty()) {
            // 访问栈顶元素
            Vertex<V, E> vertex = stack.pop();

            // 但是需要判断是否已经访问过了，如果到这里已经访问过了，直接跳过
            if (visitedVertices.contains(vertex)) continue;

            if(visitor.visit(vertex.value)) return;
            visitedVertices.add(vertex);

            for (Edge<V, E> edge : vertex.outEdges) {
                stack.push(edge.to);
            }
        }
    }

    /**
     * 非递归实现 2
     */
    @Override
    public void dfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;

        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        // 准备一个集合，用于记录已经遍历过的节点
        Set<Vertex<V, E>> visitedVertices = new HashSet<>();
        // 准备一个栈
        Stack<Vertex<V, E>> stack = new Stack<>();
        // 直接访问起点
        stack.push(beginVertex);
        if (visitor.visit(beginVertex.value)) return;
        visitedVertices.add(beginVertex); // 代表已经访问过了
        while (!stack.isEmpty()) {
            // 弹出栈顶元素 作为起点
            Vertex<V, E> fromVertex = stack.pop();

            // 根据出边，找到终点
            for (Edge<V, E> edge : fromVertex.outEdges) {
                if (visitedVertices.contains(edge.to)) continue; // 说明该终点已经被访问过了

                // 直接访问终点
                if (visitor.visit(edge.to == null ? null : edge.to.value)) return;
                visitedVertices.add(edge.to); // 代表已经访问过了

                // 将其起点和终点压入栈
                //    压入起点，是因为上面将起点弹出来了，所以还需要压入
                stack.push(edge.from);
                stack.push(edge.to);

                // 只需要选择一个终点即可
                break;
            }
        }
    }

    /**
     * 递归实现
     * @param vertex：起点
     * @param visitedVertices：已经访问过的
     * @param visitor：访问器
     */
    private void dfs(Vertex<V, E> vertex, Set<Vertex<V, E>> visitedVertices, Visitor<V> visitor) {
        // 如果需要终止，就不开始返回了
        if (visitor.stop) return;

        // 访问顶点
        visitor.stop = visitor.visit(vertex.value);

        visitedVertices.add(vertex); // 代表已经访问过了

        // 将能达到的终点，进行递归调用
        for (Edge<V, E> edge : vertex.outEdges) {
            // 将其终点进行递归调用，但是需要查看是否已经遍历过了
            if (visitedVertices.contains(edge.to)) continue;

            dfs(edge.to, visitedVertices, visitor);
        }
    }

    @Override
    public List<V> topological() {

        // 准备一个 List，用于记录 Topo 排序结果
        List<V> topoResult = new ArrayList<>();
        // 准备一个 Queue，用于过渡中间结果
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        // 准备一个 Map，用于记录顶点的入度
        Map<Vertex<V, E>, Integer> map = new HashMap<>();

        // 初始化操作
        vertices.forEach(((v, vertex) -> {
            int ins = vertex.inEdges.size(); // 获取顶点的入度

            if (ins == 0) { // 将其入队
                queue.offer(vertex);
            } else { // 记录 入度值
                map.put(vertex, ins);
            }
        }));

        // 直至队列为空
        while (!queue.isEmpty()) {
            // 队头出队，作为起点
            Vertex<V, E> vertex = queue.poll();
            // 将其加入 res 中
            topoResult.add(vertex.value);

            // 然后遍历它的出边，找到对应的终点
            for (Edge<V, E> edge : vertex.outEdges) {
                Integer ins = map.get(edge.to) - 1; // 拿到终点的入度 - 1

                if (ins == 0) { // 将其终点入队
                    queue.offer(edge.to);
                } else { // 更新入度
                    map.put(edge.to, ins);
                }
            }
        }

        return topoResult;
    }

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        return prim();
    }

    /**
     * 使用 prim 算法
     */
    private Set<EdgeInfo<V, E>> prim() {
        // 通过迭代器，随机获取一个顶点
        Iterator<Vertex<V, E>> it = vertices.values().iterator();
        if (!it.hasNext()) return null;
        Set<EdgeInfo<V, E>> set = new HashSet<>();

        final Vertex<V, E> fromVertex = it.next(); // 随机获取一个起点

        PriorityQueue<Edge<V, E>> heap = new PriorityQueue<>(edgeComparator);
        for (Edge<V, E> edge : fromVertex.outEdges) {
            heap.offer(edge);
        }
        return set;
    }

    /**
     * 使用 kruskal 算法
     */
    private Set<EdgeInfo<V, E>> kruskal() {
        Set<EdgeInfo<V, E>> set = new HashSet<>();
        // ...
        return set;
    }

}
