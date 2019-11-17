# SetCover
Use backtracking to efficiently find the minimum set cover.

**What is the minimum set cover?**

Say we have a range of numbers, 1 to N, as our universe, and we have some number of sets that contain some number of items from that range. For example, our range could be 1 to 6, and our subsets could be {1,2,3}, {2,4}, {5}, {3,6},{4,5,6}. Our goal is to use the minimum number of these sets to hold every item in our universe. In this example, the minimum set cover would be {1,2,3} and {4,5,6}, as it only uses 2 sets, and has 1, 2, 3, 4, 5 and 6. The minimum set cover problem is NP Hard, meaning that no "efficient" solution can be found- however, we can still use pruning to make backtracking more efficient than a straight N! for input.


Input file is as follows:

-Number of total items, n, in the universal set

-Number of subsets, k

-k lines representing subsets, each containing some number of items. See Test(rg6325).txt for example.



**METHODS**:


**isASolution:** returns whether or not our current array of subsets fills up our universe (1 to N).

**processSolution:** keep track of both the sets used and the number of sets.

**constructCandidates:** Construct our list of items we can add to our current solution array. For each item, if we haven't used that item, and if the item's index is greater than the max index in our current solution array (explained in pruning technique's section), and if the set is not empty, add it to our list of potential candidates.

**backtrack:** checks if our current list of subsets is a solution. If it is, process the solution; else, generate our list of potential candidates, and call backtrack on each one of them added to the array one at a time.

**PRUNING TECHNIQUES:**

-The order of subsets does not matter- therefore, only generate sets that are in ascending order to ensure we do not identify the same set twice.

-If a set is given by the input file that is completely contained in another set, remove that set before we start backtracking. For example, if the user inputs {1,2,3}, {2,3}, and {4} for a universe of 4 items, we don't have any reason to even consider {2,3}, as it is strictly worse than {1,2,3}. While this may have a seemingly relevant cost, it does not compare to the gains made from the backtracking, which is considerably larger than the O(n^2) process of the removal.

-Keep track of our best answer we've gotten so far- if at any point our current answer is equal to our best answer, we know that it can't be a better answer, so discard it.

-Run isASolution() in O(1) time: instead of checking every solution we have to see if it contains all elements of our universe, we should update our universe every time we add an item. This means that at every step, we are only checking the number of items in the set rather than the number of items in the universe.
