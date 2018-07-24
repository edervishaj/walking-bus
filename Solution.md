## Walking Bus Challenge Solution

As stated in the challenge specification, this is a special case of a spanning tree.
Namely this challenge is an instance of the Minimum Leaf Spanning Tree(MIN-
LST) which is NP-Complete because we can reduce this problem to Hamiltonian
Path setting the number of leaves of the spanning tree to 2. Since a brute force
approach to the problem is trivial and not acceptable for this challenge, I followed
a greedy approach.

If we run the Depth First Search(DSF) algorithm from a root vertex v, we can get
at the end of the execution a spanning tree rooted at v. Since our problem is
bounded on the length each node can be away from node 0, then following a
greedy approach, the local best solution to the problem is to start from node 0 and
add an arc going from the closest neighbour of node 0 to node 0. Then continue
from the added node and include the next closest neighbour, always taking into
account that the path from the last added node to 0 is within the bound. Doing this
at each step of the DFS algorithm, decreases the total covered path from node 0 to
current node and thus increases the probability to add more nodes to current path
bringing down the number of leaves of the whole spanning tree.

The solve is programmed in Java. Running the solver requires Java SE 8 Update
121. Also for reconstructing the graph in the source code, we have used the Google <a href="https://github.com/google/guava/wiki">Guava library</a>
which has a fast implementation of a graph, so we could make the program run a bit faster and also helped me write less code.
The solver can be run by the executable .jar file (which includes the Guava jar file)
from the terminal (command prompt in MS Windows):
_java –jar solvername.jar pedibus_300.sol_