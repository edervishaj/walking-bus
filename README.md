## Walking Bus Challenge<sup>[1](#footnotes1)</sup>

### Problem Description

An elementary school want to setup a walking bus system for its students.

A walking bus (pedibus in Italian) is a form of student transport for schoolchildren who,
chaperoned by two adults (usually a driver leads and a conductor follows), walk to school, in
much the same way a school bus would drive them to school. Like a traditional bus, walking
buses have a fixed route with designated bus stops in which they pick up children (The walking
bus definition is courtesy of Wikipedia).

The routes of the walking bus system must serve all students joining the initiative. A route
starts form a bus stop, goes through other bus stops and ends at the school. All students on the
route must be picked-up. Routes can merge but cannot split after merging. No student must
travel more than alpha times the shortest path from his bus stop to the school. The design of
the routes should ensure students’ safety minimizing the risk involved in the path from home to
school.

It is your duty as manager of the school to define all the routes for the walking bus system
taking into account all requirements and minimizing the number of chaperons involved. In other
words, the number of routes composing the walking bus system must be minimized.

From a more abstract point of view: consider a complete graph G = (N, A). Set N = {0..n}
is the set of nodes, node 0 is the school and all other nodes represent bus stops. Set A =
(i, j) : i ∈ N, j ∈ N is the set of arcs representing connections among nodes. For each pair of
nodes i ∈ N, j ∈ N : (i, j) ∈ A the length and the dangerousness of the shortest path, c<sub>i,j</sub>, d<sub>i,j</sub>,
from node i to node j is known; c<sub>0,j</sub> = \infty ∀j ∈ N \ {0}.

For the purpose of this challenge we only take into account graphs in which the length of
the shortest path c ij is equal to the euclidean distance from node i to node j (the triangular
inequality holds).

The walking bus problem can be seen as a special case of spanning tree problem. In this
problem we want to find the feasible spanning tree, rooted in 0, for graph G with the minimum
number of leaves (primary objective function). A spanning tree is consider as feasible in the
walking bus problem if no node is distant from 0 more than alpha times the shortest path from
the node to 0 (that is c i,0 ). The walking bus problem takes also into account an additional
objective function that is the minimization of the total dangerousness of the paths selected as
routes for the walking bus (secondary objective function). The total dangerousness is computed
as the sum of the risk (d ij ) associated with all the arcs composing the walking bus network.
This objective function is less important than the minimization of the number of leaves and it
is only used in order to differentiate among solutions with the same number of leaves.

### Results evaluation criteria

Solvers will be evaluated and ranked using a set of 10 instances. This evaluation set is not the
same as the test set provided to the participants. However instances in the evaluation set have
the same structure and size of the instances in the test set.
Three different criteria will be considered for the evaluation. From the most to the less
important:
1. **Number of leaves**. Minimizing the number of adults supervisors (number of leaves) required for the walking
bus is the main goal. Given two feasible solutions S<sub>1</sub> and S<sub>2</sub> for the same instance lets call
L<sub>1</sub> and L<sub>2</sub> the number of leaves in the spanning tree defined by S<sub>1</sub> and S<sub>2</sub> respectively. If
L<sub>1</sub> < L<sub>2</sub> then S<sub>1</sub> is better than S<sub>2</sub>.
2. **Risk**. If two feasible solutions S<sub>1</sub> and S<sub>2</sub> have the same number of leaves then the solution
described by the less dangerous (in terms of d<sub>ij</sub>) spanning three prevails. Lets call D<sub>1</sub> and
D<sub>2</sub> the sum of d<sub>ij</sub> for all arcs (i, j) used in solutions S<sub>1</sub> and S<sub>2</sub> respectively. If L<sub>1</sub> = L<sub>2</sub>
and D<sub>1</sub> < D<sub>2</sub> then S<sub>1</sub> is better than S<sub>2</sub>.
3. **Computational time**. The faster the better. If L<sub>1</sub> = L<sub>2</sub> and D<sub>1</sub> = D<sub>2</sub> then the solving time is considered. Lets
call T<sub>1</sub> and T<sub>2</sub> the time required in order to obtain S<sub>1</sub> and S<sub>2</sub> respectively. If L<sub>1</sub> = L<sub>2</sub> and
D<sub>1</sub> = D<sub>2</sub> and T<sub>1</sub> < T<sub>2</sub> then S<sub>1</sub> is better than S<sub>2</sub>.


<a id="footnotes1">1</a>: project for Foundations of Operations Research course at Politecnico di Milano