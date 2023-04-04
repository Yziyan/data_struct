package run.ciusyan.graph;

import run.ciusyan.heap.MinHeap;
import run.ciusyan.unionfind.UnionFind;

import java.util.*;

/**
 * 偏向邻接表的实现
 */
public class ListGraph<V, E> extends Graph<V, E> {

    /**
     * 存储图中所有的顶点
     */
    private Map<V, Vertex<V, E>> vertices = new HashMap<>();
    /**
     * 存储图中所有的边
     */
    private Set<Edge<V, E>> edges = new HashSet<>();

    /**
     * 边的比较器
     */
    private Comparator<Edge<V, E>> edgeComparator = (e1, e2) ->
        weightManager.compare(e1.weight, e2.weight);

    public ListGraph() {}

    public ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
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

        EdgeInfo<V, E> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
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
        return kruskal();
    }

    @Override
    public Map<V, PathInfo<V, E>> shortPath(V src) {
        return dijkstra(src);
    }

    /**
     * 使用 Bellman-Ford 算法求出最短路径
     */
    private Map<V, PathInfo<V, E>> bellmanFord(V src) {
        Vertex<V, E> srcVertex = vertices.get(src);
        if (srcVertex == null) return null;

        // 返回结果
        Map<V, PathInfo<V, E>> computedPath = new HashMap<>();
        // 需要对 computedPath 进行初始化，否则之后加入循环时，会一直获取不到，fromPath
        // 至少要将源点加入进去
        PathInfo<V, E> srcPathInfo = new PathInfo<>();
        // 因为这里是泛型，需要外界告诉我们，它的零值是什么，所以又可以在权值管理器增加方法
        srcPathInfo.weight = weightManager.zreo();
        // 这里不需要添加一条边进去了，只要有权值即可
        // 要不然之后的路径会变成 源点 - 源点 - 其他点 - ...
        computedPath.put(src, srcPathInfo);

        // 进行 V - 1 次，松弛操作
        int count = vertices.size() - 1;
        for (int i = 0; i < count; i++) {

            // 尝试对所有边进行松弛操作
            for (Edge<V, E> edge : edges) {
                // 尝试获取源点，到起点的最短路径
                PathInfo<V, E> fromPath = computedPath.get(edge.from.value);
                // 代表还没有路，能够从源点到达此条边
                if (fromPath == null) continue;

                // 松弛操作
                relaxForBF(edge, fromPath, computedPath);
            }
        }

        // 判断是否有负权环
        for (Edge<V, E> edge : edges) {
            // 尝试获取源点，到起点的最短路径
            PathInfo<V, E> fromPath = computedPath.get(edge.from.value);
            // 代表还没有路，能够从源点到达此条边
            if (fromPath == null) continue;
            if (relaxForBF(edge, fromPath, computedPath)) {
                System.out.println("有负权环");
                return null;
            }
        }

        // 计算出所有点的最短路径后，将其源点从集合中删除
        computedPath.remove(src);

        return computedPath;
    }


    /**
     * 使用 Dijkstra 算法求出最短路径
     */
    private Map<V, PathInfo<V,E>> dijkstra(V src) {
        Vertex<V, E> srcVertex = vertices.get(src);
        if (srcVertex == null) return null;

        // 返回结果
        Map<V, PathInfo<V, E>> computedPath = new HashMap<>();
        // 用于记录中间结果
        Map<Vertex<V, E>, PathInfo<V, E>> paths = new HashMap<>();

        // 默认将源点放入，
        PathInfo<V, E> srcPathInfo = new PathInfo<>();
        srcPathInfo.weight = weightManager.zreo();
        paths.put(srcVertex, srcPathInfo);

        // 初始化 paths，这个操作，可以交给下面的代码去做，当作松弛即可
//        for (Edge<V, E> edge : srcVertex.outEdges) {
//            // 从源点到每一个终点的距离
//            PathInfo<V, E> pathInfo = new PathInfo<>();
//            pathInfo.weight = edge.weight;
//            pathInfo.edgeInfos.add(edge.info());
//            paths.put(edge.to, pathInfo);
//        }

        while (!paths.isEmpty()) {

            // 获取 paths 中的最小值
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> minPath = getMinPath(paths);
            Vertex<V, E> vertex = minPath.getKey();

            // 将此顶点在 paths 中删除
            PathInfo<V, E> pathInfo = paths.remove(vertex);
            // 添加到结果中
            computedPath.put(vertex.value, pathInfo);

            // 对刚算出最短路径的顶点，进行松弛操作
            for (Edge<V, E> edge : vertex.outEdges) {
                // 如果已经得到结果了，就别往计算了
                // 或者，如果这个点，是源点，也别往下走了
                if (computedPath.containsKey(edge.to.value)) continue;

                // 松弛操作
                relaxForDijkstra(edge, pathInfo, paths);
            }
        }

        // 不管结果种有没有，直接删除源点
        computedPath.remove(src);

        return computedPath;
    }

    @Override
    public Map<V, Map<V, PathInfo<V, E>>> shortPath() {
        // 返回结果
        Map<V, Map<V, PathInfo<V, E>>> paths = new HashMap<>();

        // 初始化 paths，将所有的边，from -> to 默认当作最短路径
        edges.forEach((edge -> {
            Map<V, PathInfo<V, E>> fromPaths = paths.get(edge.from.value);

            // 可能为空
            if (fromPaths == null) {
                fromPaths = new HashMap<>();
            }

            // 能来到这里，fromPaths 肯定不为空了，
            // 初始化，fromPath，将其放入 fromPaths 中
            PathInfo<V, E> fromPath = new PathInfo<>();
            fromPath.weight = edge.weight;
            fromPath.edgeInfos.add(edge.info());
            fromPaths.put(edge.to.value, fromPath);

            // 初始化 fromPaths，将其放入 paths 中
            paths.put(edge.from.value, fromPaths);
        }));

        // 三层遍历，不断寻找某两个顶点之间的最短路径
        // 1 -> 3 or 1 -> 2 -> 3
        //      只能将 2 放最前面，否则可能会有问题
        vertices.forEach((v2, vertex1) -> {
            vertices.forEach((v1, vertex2) -> {
                vertices.forEach((v3, vertex3) -> {
                    // 如果，它们之间任意两个相等，说明是 源点 -> 源点，没必要执行
                    if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;

                    // 获取 1->2 的路径信息，可能没有
                    PathInfo<V, E> path12 = getPath(v1, v2, paths);
                    if (path12 == null) return;

                    // 获取 2->3 的路径信息，可能没有
                    PathInfo<V, E> path23 = getPath(v2, v3, paths);
                    if (path23 == null) return;

                    // 获取 1->3 的路径信息，可能没有
                    PathInfo<V, E> path13 = getPath(v1, v3, paths);

                    // dist(1, 2) + dist(2, 3)
                    E newWeight = weightManager.add(path12.weight, path23.weight);

                    // 如果 13 之间有路，并且新权值没有旧权值小，直接跳过此次循环
                    if (path13 != null && weightManager.compare(newWeight, path13.weight) >= 0) return;

                    if (path13 == null) {
                        // 说明之前没有路，需要添加一条路
                        path13 = new PathInfo<>();
                        // 给 path13 之间，增加一条路
                        paths.get(v1).put(v3, path13);
                    } else { // 说明以前有路，但是现在的路更短，清空以前的路径
                        path13.edgeInfos.clear();
                    }

                    // 更新权值和路径，以前的路是 1->3 ，变为 1->2->3
                    path13.edgeInfos.addAll(path12.edgeInfos);
                    path13.edgeInfos.addAll(path23.edgeInfos);
                    path13.weight = newWeight;
                });
            });
        });

        return paths;
    }

    /**
     * 获取路径
     * @param from：起点
     * @param to：终点
     * @param paths：所有路径信息
     * @return ：从 from -> to 的路径信息
     */
    private PathInfo<V, E> getPath(V from, V to, Map<V, Map<V, PathInfo<V, E>>> paths) {
        Map<V, PathInfo<V, E>> fromPath = paths.get(from);

        return fromPath == null ? null : fromPath.get(to);
    }

    /**
     * 松弛操作 —— 对于 Bellman-Ford 而言
     * @param edge：对哪条边进行松弛操作
     * @param fromPath：那条边的路径信息
     * @param paths：还未被确定的最短路径（未被提起来的小石子）
     * @return ：是否松弛成功，用于查看图是否有负权环
     */
    private boolean relaxForBF(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<V, PathInfo<V, E>> paths) {
        // (srcVertex 到 vertex) + (vertex 到 vertex.to)
        // 也就是现在路径上的最小值 + 这条边的路径长度
        E newWeight = weightManager.add(fromPath.weight, edge.weight);

        PathInfo<V, E> oldInfo = paths.get(edge.to.value);
        if (oldInfo != null && weightManager.compare(newWeight, oldInfo.weight) >= 0) return false;

        if (oldInfo == null) {
            oldInfo = new PathInfo<>();
            paths.put(edge.to.value, oldInfo);
        } else {
            oldInfo.edgeInfos.clear();
        }

        oldInfo.edgeInfos.addAll(fromPath.edgeInfos);
        oldInfo.edgeInfos.add(edge.info());
        oldInfo.weight = newWeight;

        return true;
    }

    /**
     * 松弛操作 —— 对于 Dijkstra 而言
     * @param edge：对哪条边进行松弛操作
     * @param fromPath：刚被提起来小石子，这条边的路径信息
     * @param paths：还未被确定的最短路径（未被提起来的小石子）
     */
    private void relaxForDijkstra(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<Vertex<V, E>, PathInfo<V, E>> paths) {

        // (srcVertex 到 vertex) + (vertex 到 vertex.to)
        E newWeight = weightManager.add(fromPath.weight, edge.weight);

        // 将新权值与，旧权值比较，如果新路径还要短，就更新路径值
        // 当然，如果以前都没有路，说明获取出来是空的，那么现在就是最短的
        PathInfo<V, E> oldInfo = paths.get(edge.to);
        if (oldInfo != null && weightManager.compare(newWeight, oldInfo.weight) >= 0) return;

        // 来到这里说明，有更短的路径，更新路径
        if (oldInfo == null) {
            // 说明以前，没有路径存在，刚有一条路，得初始化一下
            oldInfo = new PathInfo<>();
            paths.put(edge.to, oldInfo);
        } else {
            // 如果以前有路径，将其路径清空，因为要更新路径了
            oldInfo.edgeInfos.clear();
        }

        // 更新 路径 和 长度
        // 将 源点到，此顶点的路径先添加进去
        // fromPath.edgeInfos ：是刚被提起来的小石子的最短路径，
        oldInfo.edgeInfos.addAll(fromPath.edgeInfos);
        // 载加这条新的路径
        oldInfo.edgeInfos.add(edge.info());
        oldInfo.weight = newWeight;
    }

    /**
     * 获取最小路径
     * @param paths：所有顶点的路径
     * @return ：最小路径的 键值对
     */
    private Map.Entry<Vertex<V, E>, PathInfo<V, E>> getMinPath(Map<Vertex<V, E>, PathInfo<V, E>> paths) {

        // 使用迭代器遍历
        Iterator<Map.Entry<Vertex<V, E>, PathInfo<V, E>>> it = paths.entrySet().iterator();
        // 因为上面会判断，这里大胆的取
        Map.Entry<Vertex<V, E>, PathInfo<V, E>> minPath = it.next();

        // 遍历，找出最小值
        while (it.hasNext()) {
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> next = it.next();
            if (weightManager.compare(next.getValue().weight, minPath.getValue().weight) < 0) {
                // 说明比 最小的 还要小
                minPath = next;
            }
        }

        return minPath;
    }

    /**
     * 使用 prim 算法
     */
    private Set<EdgeInfo<V, E>> prim() {
        // 通过迭代器，随机获取一个顶点
        Iterator<Vertex<V, E>> it = vertices.values().iterator();
        if (!it.hasNext()) return null;
        // 返回结果的集合
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        Vertex<V, E> fromVertex = it.next(); // 随机获取一个起点
        // 代表已经切分的集合
        Set<Vertex<V, E>> cuts = new HashSet<>();
        // 将起点放入已切分集合中
        cuts.add(fromVertex);

        // 使用最小堆，传入比较器、并且原地建堆
        MinHeap<Edge<V, E>> heap = new MinHeap<>(fromVertex.outEdges, edgeComparator);

        // 拿到所有顶点数量
        int vertexSize = vertices.size();
        // 至少保证堆里有元素，才加入循环，
        // 但如果 已切分的顶点数 = 顶点总数。可以提前退出循环
        while (!heap.isEmpty() && cuts.size() < vertexSize) {
            // 直接删除堆顶元素，然后将其加入返回的集合中
            Edge<V, E> minEdge = heap.remove();
            // 代表已经切分过了，不用选这一条边了
            if (cuts.contains(minEdge.to)) continue;

            edgeInfos.add(minEdge.info()); // 加入结果集
            cuts.add(minEdge.to); // 将此顶点，置为已切分

            // 将终点作为起点，将它的出边，加入堆中，进行下一轮比较
            heap.addAll(minEdge.to.outEdges);
        }

        return edgeInfos;
    }

    /**
     * 使用 kruskal 算法
     */
    private Set<EdgeInfo<V, E>> kruskal() {
        int vertexSize = vertices.size() - 1;
        if (vertexSize <= -1) return null;

        // 用于装返回值
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();

        // 将所有边，原地入堆
        MinHeap<Edge<V, E>> heap = new MinHeap<>(edges, edgeComparator);

        // 准备一个并查集，用于判断是否有环
        UnionFind<Vertex<V, E>> unionFind = new UnionFind<>();
        // 并且初始化每个顶点集（每一个顶点，作为一个单独的集合）
        unionFind.makeSet(vertices.values());

        // 至少要保证堆里有元素，如果 结果边的数量 = 顶点数 - 1，可以提前退出循环
        while (!heap.isEmpty() && edgeInfos.size() < vertexSize) {
            // 删除堆顶元素，取出最小的边
            Edge<V, E> minEdge = heap.remove();
            // 如果最小边的起点和终点都在一个顶点集中了，说明这条边连接后会成环
            if (unionFind.isSame(minEdge.from, minEdge.to)) continue;

            // 来到这里说明不会形成环，加入最小的一条边
            edgeInfos.add(minEdge.info());
            // 并且需要将这条边的 起点和终点并入一个顶点集
            unionFind.union(minEdge.from, minEdge.to);
        }

        return edgeInfos;
    }


    // 简单版 Dijisktra
//    public Map<V, E> shortPath(V src) {
//        Vertex<V, E> srcVertex = vertices.get(src);
//        if (srcVertex == null) return null;
//
//        // 返回结果
//        Map<V, E> computedPath = new HashMap<>();
//        // 用于记录中间结果
//        Map<Vertex<V, E>, E> paths = new HashMap<>();
//
//        // 初始化 paths
//        for (Edge<V, E> edge : srcVertex.outEdges) {
//            // 从源点到每一个终点的距离
//            paths.put(edge.to, edge.weight);
//        }
//
//        while (!paths.isEmpty()) {
//
//            // 获取 paths 中的最小值
//            Map.Entry<Vertex<V, E>, E> minPath = getMinPath(paths);
//            Vertex<V, E> vertex = minPath.getKey();
//
//            // 将此顶点在 paths 中删除
//            E weight = paths.remove(vertex);
//            // 添加到结果中
//            computedPath.put(vertex.value, weight);
//
//            // 对刚算出最短路径的顶点，进行松弛操作
//            for (Edge<V, E> edge : vertex.outEdges) {
//                // 如果已经得到结果了，就别往计算了
//                // 或者，如果这个点，是源点，也别往下走了
//                if (computedPath.containsKey(edge.to.value)) continue;
//
//                // (srcVertex 到 vertex) + (vertex 到 vertex.to)
//                E newWeight = weightManager.add(weight, edge.weight);
//
//                // 将新权值与，旧权值比较，如果新路径还要短，就更新路径值
//                // 当然，如果以前都没有路，说明获取出来是空的，那么现在就是最短的
//                E oldWeight = paths.get(edge.to);
//                if (oldWeight == null || weightManager.compare(newWeight, oldWeight) < 0) {
//                    // 来到这里说明，有更短的路径，更新路径
//                    paths.put(edge.to, newWeight);
//                }
//            }
//        }
//
//        // 不管有没有将源点加入，都删除掉
//        computedPath.remove(src);
//
//        return computedPath;
//    }
//
//    /**
//     * 获取最小路径
//     * @param paths：所有顶点的路径
//     * @return ：最小路径的 键值对
//     */
//    private Map.Entry<Vertex<V, E>, E> getMinPath(Map<Vertex<V, E>, E> paths) {
//
//        // 使用迭代器遍历
//        Iterator<Map.Entry<Vertex<V, E>, E>> it = paths.entrySet().iterator();
//        // 因为上面会判断，这里大胆的取
//        Map.Entry<Vertex<V, E>, E> minPath = it.next();
//
//        // 遍历，找出最小值
//        while (it.hasNext()) {
//            Map.Entry<Vertex<V, E>, E> next = it.next();
//            if (weightManager.compare(next.getValue(), minPath.getValue()) < 0) {
//                // 说明比 最小的 还要小
//                minPath = next;
//            }
//        }
//
//        return minPath;
//    }


}
