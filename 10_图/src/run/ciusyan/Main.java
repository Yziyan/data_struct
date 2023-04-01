package run.ciusyan;

import run.ciusyan.graph.Graph;
import run.ciusyan.graph.ListGraph;

public class Main {
    public static void main(String[] args) {
        testBfs();
    }

    static void testDfs() {
        Graph<Object, Double> graph = undirectedGraph(Data.DFS_01);
        graph.dfs(1, new ListGraph.Visitor<>() {
            @Override
            protected boolean visit(Object o) {
                Integer v = (Integer) o;
                System.out.println(v);
                return v == 7;
            }
        });
    }

    static void testBfs() {
        Graph<Object, Double> graph = undirectedGraph(Data.BFS_01);
        graph.bfs("A", new ListGraph.Visitor<>() {
            @Override
            protected boolean visit(Object o) {
                String v = (String) o;
                System.out.println(v);
                return "I".equals(v);
            }
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
        Graph<Object, Double> graph = new ListGraph<>();
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
        Graph<Object, Double> graph = new ListGraph<>();
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
