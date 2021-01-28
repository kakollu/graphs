# graphs

This algorithm is from Prof. Tim Roughgarden's course.
    Honor code: This is a modified a bit from the assignment, but still has overlap with the assignment so please use your judgement.

    A Dynamic programming solution for Traveling Salesman Problem - TSP, O(2^n) instead of n! bruteforce.
    It brings up a interesting use of bits patterns as the sets naturally from the integers.

    Humans beat TSP at small numbers by using heuristics and getting good enough solutions.

    The algorithm as explained in the course.
    Let A = 2D array indexed by subsets S of {1,2,..n} that  contain 1 and destinations j in {1, 2, .. n}

    Base case A[S,1] = 0 if S  = {1} , +inf otherwise

    For m = 2,3,4,..,n ; m is sub problem size
      For each subset S of {1,2,..,n} of size m that contain 1
        For each j in S but j!=1
          A[S,j] = min k in S, k!=j { A[S-{j},k] + C[k,j] }
    return min j = 2, n { A[{1,2,..n}, 1] + C[j, 1] }

    In the following implementation, I will be assigning 1 bit per vertex/node of the graph.
    vertices are numbered 0,1,2,4,..
    The vertex 0 is the node 1 referred to in the algorithm and its position is implied.

    This function returns the shortest TSP tour length.
