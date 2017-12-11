package interview;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * function description：
 *
 * 路径规划 假设现在有一个有向无环图，每个节点上都带有正数权重。我们希望找到一 条最优路径，使得这个路径上经过的节点的权重之和最大。
 * 输入：n个节点，m个路径，起点 输出：最优路径的权重值之和
 * 
 * 给定起点 s,找最大权重 最优性问题，首先想到是 动态规划，
 * 
 * 一个数组 sum[] 表示 s 到各节点的最大距离
 * 
 * sum(s) = s, 表明 s到自己的权重 即为自身的值
 * 
 * 其中一个 点 u，对u的邻居节点 v sum(s,v) = max(sum(s,v),sum(s,u) + sum(u,v))
 * 每个 Node 元素增加了增加了一个sun 用于记录每个点到start 的最大距离
 * visited 变量，用于控制操作，避免重复
 * neighbors 用来记录有向图的邻居
 * 
 * 采用广度优先遍历该图，并按照公式计算每个点的maxSum，并记录最大值
 * 
 * 
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu </a>
 * @version v 1.0 Create: 18 Nov 2017 2:34:46 pm
 */
public class MaxSumofPath {

	static class Node {
		int val; // weight value
		boolean visited; // add visited to avoid the circle 
		List<Node> neighbors; // neighbor nodes
		int sum; // sum value
		Node(int val) {
			this.val = val;
			this.visited = false;
			this.neighbors = new ArrayList<Node>();
			this.sum = val;
		}
	}

	/**
	 * @function:
	 * @param args
	 * @author: Rambo Zhu 18 Nov 2017 2:34:46 pm
	 */
	public static void main(String[] args) {
		
		Node head = input();
		int max = maxSum(head); //未考虑有环情况 
		System.out.println("max sum is " + max); // max sum is 23
		
		head = input();
		max = maxSumWithCircle(head); //考虑了有环情况 
		System.out.println("max sum is " + max); // max sum is 18

	}

	
	public static Node input() {
		// nodes
		Node a = new Node(2);
		Node b = new Node(5);
		Node c = new Node(3);
		Node d = new Node(8);
		
		// neighbors
		a.neighbors.add(b);
		a.neighbors.add(c);
		b.neighbors.add(c);
		c.neighbors.add(d);
		d.neighbors.add(b); // test the possible circle 
		
		return a;
	}
	
	
	public static int maxSum(Node start) {
		if(start == null) return 0;
		int max = 0;
		Queue<Node> q = new LinkedList<Node>(); // 用队列存放依次要遍历的元素
		q.offer(start);
		
		while (!q.isEmpty()) {
			Node cur = q.poll();
			if (!cur.visited) {
				cur.visited = true;
				if (!cur.neighbors.isEmpty())
					for (Node n : cur.neighbors){
						if(cur.sum + n.val > n.sum) n.sum = cur.sum + n.val;
						if(n.sum > max) max = n.sum;
						q.offer(n);
					}
			}
		}
		return max;
	}
	public static int maxSumWithCircle(Node start) {
		if(start == null) return 0;
		int max = 0;
		Queue<Node> q = new LinkedList<Node>(); // 用队列存放依次要遍历的元素
		List<Node> ls = new ArrayList<Node>(); //保存遍历结果 

		q.offer(start);
		
		while (!q.isEmpty()) {
			Node cur = q.poll();
			if (!ls.contains(cur)) {
				ls.add(cur);
				if (!cur.neighbors.isEmpty())
					for (Node n : cur.neighbors){
						if(!ls.contains(n) && cur.sum + n.val > n.sum) n.sum = cur.sum + n.val;
						if(n.sum > max) max = n.sum;
						q.offer(n);
					}
			}
		}
		return max;
	}
	
//	广度优先遍历
	private static List<Node> bfs(Node start) {
		List<Node> ls = new ArrayList<Node>(); //保存遍历结果 
		Queue<Node> q = new LinkedList<Node>(); // 用队列存放依次要遍历的元素
		q.offer(start);
		while (!q.isEmpty()) {
			Node cur = q.poll();
			if (!cur.visited) {
				cur.visited = true;
				ls.add(cur);
				if (!cur.neighbors.isEmpty())
					for (Node n : cur.neighbors){
						q.offer(n);
					}
			}
		}
		return ls;
	}
	
//	深度优先遍历
	private static void dfs(Node node, List<Node> ls) {
		// 判断是否遍历过
		if (node.visited) {
			return;
		}
		node.visited = true;
		ls.add(node);
		for (Node n : node.neighbors) {
			dfs(n, ls);
		}
	}
}
