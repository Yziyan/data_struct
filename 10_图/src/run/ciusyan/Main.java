package run.ciusyan;

import run.ciusyan.graph.Graph;
import run.ciusyan.graph.ListGraph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    static Graph.WeightManager<Double> weightManager = new Graph.WeightManager<>() {
        @Override
        public int compare(Double w1, Double w2) {
            return w1.compareTo(w2);
        }

        @Override
        public Double add(Double w1, Double w2) {
            return w1 + w2;
        }
    };

    public static void main(String[] args) {
        testSp();
    }

    static void testSp() {
        Graph<Object, Double> graph = undirectedGraph(Data.SP);
        Map<Object, Double> paths = graph.shortPath("A");

        System.out.println(paths);
    }

    static void testMst() {
        Graph<Object, Double> graph = undirectedGraph(Data.MST_02);
        Set<Graph.EdgeInfo<Object, Double>> infos = graph.mst();
        for (Graph.EdgeInfo<Object, Double> info : infos) {
            System.out.println(info);
        }
    }

    static void testTopo() {
        Graph<Object, Double> graph = directedGraph(Data.TOPO);
        List<Object> topoRes = graph.topological();
        System.out.println(topoRes);
    }

    static void testDfs() {
        Graph<Object, Double> graph = directedGraph(Data.DFS_02);
        graph.dfs("a", (Object o) -> {
            System.out.println(o);
            return o.equals("f");
        });
    }

    static void testBfs() {
        Graph<Object, Double> graph = undirectedGraph(Data.BFS_01);
        graph.bfs("A", (Object o) -> {
            System.out.println(o);
            return o.equals("I");
        });
    }

    static void test01() {
        ListGraph<String, Integer> graph = new ListGraph<>();
        graph.addEdge("v1", "v0", 9);
        graph.addEdge("v1", "v2", 3);
        graph.addEdge("v2", "v0", 2);
        graph.addEdge("v2", "v3", 5);
        graph.addEdge("v3", "v4", 1);
        graph.addEdge("v0", "v4", 6);
        // graph.removeEdge("v0", "v4");
        graph.removeVertex("v0");

        graph.print();
    }


    /**
     * 有向图
     */
    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
            }
        }
        return graph;
    }

    /**
     * 无向图
     * @param data
     * @return
     */
    private static Graph<Object, Double> undirectedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
                graph.addEdge(edge[1], edge[0]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
                graph.addEdge(edge[1], edge[0], weight);
            }
        }
        return graph;
    }
}
