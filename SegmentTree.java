class SegmentTree { 
    int[] segmentTree; // Array that stores segment tree nodes
  
    // Constructor to construct segment tree from the given array
    SegmentTree(int[] inputArray, int arraySize) { 
        // Calculate height of the segment tree
        int height = (int) (Math.ceil(Math.log(arraySize) / Math.log(2))); 
  
        // Maximum size of the segment tree
        int maxSize = 2 * (int) Math.pow(2, height) - 1; 
  
        segmentTree = new int[maxSize]; // Allocate memory for segment tree
  
        buildSegmentTree(inputArray, 0, arraySize - 1, 0); 
    } 
  
    // Utility function to get the middle index from start and end indexes
    int findMiddle(int start, int end) { 
        return start + (end - start) / 2; 
    } 
  
    // Recursive function to get the sum of values in the given range
    int calculateRangeSum(int segmentStart, int segmentEnd, int queryStart, int queryEnd, int currentIndex) { 
        // If the segment of this node is a part of the given range, return the sum of the segment
        if (queryStart <= segmentStart && queryEnd >= segmentEnd) 
            return segmentTree[currentIndex]; 
  
        // If the segment of this node is outside the given range 
        if (segmentEnd < queryStart || segmentStart > queryEnd) 
            return 0; 
  
        // If a part of this segment overlaps with the given range 
        int middle = findMiddle(segmentStart, segmentEnd); 
        return calculateRangeSum(segmentStart, middle, queryStart, queryEnd, 2 * currentIndex + 1) + 
               calculateRangeSum(middle + 1, segmentEnd, queryStart, queryEnd, 2 * currentIndex + 2); 
    } 
  
    // Recursive function to update the nodes which include the given index in their range
    void updateSegmentTree(int segmentStart, int segmentEnd, int index, int valueDifference, int currentIndex) { 
        // Base Case: If the input index lies outside the range of this segment 
        if (index < segmentStart || index > segmentEnd) 
            return; 
  
        // If the input index is within the range of this node, update the value of the node and its children 
        segmentTree[currentIndex] += valueDifference; 
        if (segmentStart != segmentEnd) { 
            int middle = findMiddle(segmentStart, segmentEnd); 
            updateSegmentTree(segmentStart, middle, index, valueDifference, 2 * currentIndex + 1); 
            updateSegmentTree(middle + 1, segmentEnd, index, valueDifference, 2 * currentIndex + 2); 
        } 
    } 
  
    // Function to update a value in the input array and segment tree
    void updateValue(int[] inputArray, int arraySize, int index, int newValue) { 
        // Check for erroneous input index 
        if (index < 0 || index >= arraySize) { 
            System.out.println("Invalid Input"); 
            return; 
        } 
  
        // Get the difference between the new value and the old value 
        int valueDifference = newValue - inputArray[index]; 
  
        // Update the value in the input array 
        inputArray[index] = newValue; 
  
        // Update the values of nodes in the segment tree 
        updateSegmentTree(0, arraySize - 1, index, valueDifference, 0); 
    } 
  
    // Return the sum of elements in the range from index queryStart to queryEnd
    int getRangeSum(int arraySize, int queryStart, int queryEnd) { 
        // Check for erroneous input values 
        if (queryStart < 0 || queryEnd >= arraySize || queryStart > queryEnd) { 
            System.out.println("Invalid Input"); 
            return -1; 
        } 
        return calculateRangeSum(0, arraySize - 1, queryStart, queryEnd, 0); 
    } 
  
    // Recursive function that constructs the segment tree for array[segmentStart..segmentEnd]
    int buildSegmentTree(int[] inputArray, int segmentStart, int segmentEnd, int currentIndex) { 
        // If there is one element in the array, store it in the current node of the segment tree and return
        if (segmentStart == segmentEnd) { 
            segmentTree[currentIndex] = inputArray[segmentStart]; 
            return inputArray[segmentStart]; 
        } 
  
        // If there are more than one element, recur for left and right subtrees and store the sum of values in this node
        int middle = findMiddle(segmentStart, segmentEnd); 
        segmentTree[currentIndex] = buildSegmentTree(inputArray, segmentStart, middle, 2 * currentIndex + 1) + 
                                    buildSegmentTree(inputArray, middle + 1, segmentEnd, 2 * currentIndex + 2); 
        return segmentTree[currentIndex]; 
    } 
  
    // Driver program to test the above functions
    public static void main(String[] args) { 
        int[] inputArray = {1, 3, 5, 7, 9, 11}; 
        int arraySize = inputArray.length; 
        SegmentTree segmentTree = new SegmentTree(inputArray, arraySize); 
  
        // Print sum of values in array from index 1 to 3 
        System.out.println("Sum of values in given range = " + segmentTree.getRangeSum(arraySize, 1, 3)); 
  
        // Update: set inputArray[1] = 10 and update corresponding segment tree nodes 
        segmentTree.updateValue(inputArray, arraySize, 1, 10); 
  
        // Find sum after the value is updated 
        System.out.println("Updated sum of values in given range = " + segmentTree.getRangeSum(arraySize, 1, 3)); 
    } 
}
