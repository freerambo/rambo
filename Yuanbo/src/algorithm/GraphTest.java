package algorithm;

/*4. ͼ
ͼ��ص�������Ҫ�������������������depth first search���͹������������breath first search����

������һ���򵥵�ͼ�������������ʵ�֡�

1) ����GraphNode*/

class GraphNode{ 

    int val;

    GraphNode next;

    GraphNode[] neighbors;

    boolean visited;
 

    GraphNode(int x) {

        val = x;

    }
 

    GraphNode(int x, GraphNode[] n){

        val = x;

        neighbors = n;

    }
 

    public String toString(){

        return "value: "+ this.val; 

    }
}
//2) ����һ������Queue


class GraphQueue{

    GraphNode first, last;
 

    public void enqueue(GraphNode n){

        if(first == null){

            first = n;

            last = first;

        }else{

            last.next = n;

            last = n;

        }

    }
 

    public GraphNode dequeue(){

        if(first == null){

            return null;

        }else{

            GraphNode temp = new GraphNode(first.val, first.neighbors);

            first = first.next;

            return temp;

        }   

    }
}

//2) ����һ��Stack
class GraphStack{
	GraphNode top;
	
	 public GraphNode peek(){

	        if(top != null){

	            return top;

	        }
	 

	        return null;

	    }
	 GraphNode pop(){
		  if(top != null){

			  GraphNode temp = new GraphNode(top.val, top.neighbors);
			  top = top.next;
	          return temp;
	        }
        return null;
	}
	 
	void push(GraphNode node){
		  if(node != null){

	        	node.next = top;
	        	top = node;
	       }
	 		
	}
}


//3) �ö���Queueʵ�ֹ����������

public class GraphTest {
 

    public static void main(String[] args) {

        GraphNode n1 = new GraphNode(1); 

        GraphNode n2 = new GraphNode(2); 

        GraphNode n3 = new GraphNode(3); 

        GraphNode n4 = new GraphNode(4); 

        GraphNode n5 = new GraphNode(5); 
 

        n1.neighbors = new GraphNode[]{n2,n3,n5};

        n2.neighbors = new GraphNode[]{n1,n4};

        n3.neighbors = new GraphNode[]{n1,n4,n5};

        n4.neighbors = new GraphNode[]{n2,n3,n5};

        n5.neighbors = new GraphNode[]{n1,n3,n4};
 

//      breathFirstSearch(n1, 5);

        deepFirstSearch(n1, 5);
    }

    // DEEP FIRST  �ݹ�ʵ���������
    public static void deepFirstSearch(GraphNode root, int x){
    	 if(root.val == x)
             System.out.println("find in root");
         
         root.visited = true;
                  
         if(root != null){
        	 GraphNode c = root;
        	    for(GraphNode n: c.neighbors){
        	    	 if(!n.visited){

                         System.out.print(n + " ");

                         n.visited = true;

                         if(n.val == x){
                             System.out.println("Find "+n);
                             break;
                         }
                         deepFirstSearch(n, x);
                     }
        	    }
         }
    }
    // BREATHFIRST

    public static void breathFirstSearch(GraphNode root, int x){

        if(root.val == x)

            System.out.println("find in root");
 

        GraphQueue queue = new GraphQueue();

        root.visited = true;

        queue.enqueue(root);
 

        while(queue.first != null){

            GraphNode c = (GraphNode) queue.dequeue();

            for(GraphNode n: c.neighbors){
 

                if(!n.visited){

                    System.out.print(n + " ");

                    n.visited = true;

                    if(n.val == x){

                        System.out.println("Find "+n);

                    }
                    queue.enqueue(n);
                }

            }

        }

    }
}

//Output:
//
//value: 2 value: 3 value: 5 Find value: 5
//value: 4

