import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void testIsEmptyAndSize() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        // Test on empty deque
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);

        // Test on after adding one element
        lld1.addFirst("front");
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(1);

        // Test on after adding more elements
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    public void testGet() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        // Test get method on empty deque
        assertThat(lld1.get(0)).isNull();
        assertThat(lld1.get(-1)).isNull();
        assertThat(lld1.get(100)).isNull();

        // Add some elements to the deque
        lld1.addLast("A");
        lld1.addLast("B");
        lld1.addLast("C");

        // Test get method on valid indices
        assertThat(lld1.get(0)).isEqualTo("A");
        assertThat(lld1.get(1)).isEqualTo("B");
        assertThat(lld1.get(2)).isEqualTo("C");

        // Test boundary cases
        assertThat(lld1.get(-1)).isNull();
        assertThat(lld1.get(3)).isNull();
        assertThat(lld1.get(100)).isNull();

        // Test get method on only one element
        Deque61B<String> singleItemDeque = new LinkedListDeque61B<>();
        singleItemDeque.addFirst("X");
        assertThat(singleItemDeque.get(0)).isEqualTo("X");
        assertThat(singleItemDeque.get(1)).isNull();
        assertThat(singleItemDeque.get(-1)).isNull();
    }

    @Test
    /** Test performance of `get` method on large deque. */
    public void testGetWithLargeDeque() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        int size = 1000;

        // add 1000 elements
        for (int i = 0; i < size; i++) {
            lld1.addLast(i);
        }

        // Test random indices
        for (int i = 0; i < size; i++) {
            int index = (int)(Math.random() * size);
            assertThat(lld1.get(index)).isEqualTo(index);
        }

        // Test boundary cases
        assertThat(lld1.get(-1)).isNull();
        assertThat(lld1.get(size)).isNull();
        assertThat(lld1.get(10000)).isNull();
    }
    @Test
    public void testGetRecursive() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        // Test get method on empty deque
        assertThat(lld1.getRecursive(0)).isNull();
        assertThat(lld1.getRecursive(-1)).isNull();
        assertThat(lld1.getRecursive(100)).isNull();

        // Add some elements to the deque
        lld1.addLast("A");
        lld1.addLast("B");
        lld1.addLast("C");

        // Test get method on valid indices
        assertThat(lld1.getRecursive(0)).isEqualTo("A");
        assertThat(lld1.getRecursive(1)).isEqualTo("B");
        assertThat(lld1.getRecursive(2)).isEqualTo("C");

        // Test boundary cases
        assertThat(lld1.getRecursive(-1)).isNull();
        assertThat(lld1.getRecursive(3)).isNull();
        assertThat(lld1.getRecursive(100)).isNull();

        // Test get method on only one element
        Deque61B<String> singleItemDeque = new LinkedListDeque61B<>();
        singleItemDeque.addFirst("X");
        assertThat(singleItemDeque.getRecursive(0)).isEqualTo("X");
        assertThat(singleItemDeque.getRecursive(1)).isNull();
        assertThat(singleItemDeque.getRecursive(-1)).isNull();
    }

    @Test
    /** Test performance of `get` method on large deque. */
    public void testGetRecursiveWithLargeDeque() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        int size = 1000;

        // add 1000 elements
        for (int i = 0; i < size; i++) {
            lld1.addLast(i);
        }

        // Test random indices
        for (int i = 0; i < size; i++) {
            int index = (int)(Math.random() * size);
            assertThat(lld1.getRecursive(index)).isEqualTo(index);
        }

        // Test boundary cases
        assertThat(lld1.getRecursive(-1)).isNull();
        assertThat(lld1.getRecursive(size)).isNull();
        assertThat(lld1.getRecursive(10000)).isNull();
    }


    @Test
    public void testRemoveFirstAndRemoveLast() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // Add some elements to the deque
        lld1.addLast(10);
        lld1.addLast(20);
        lld1.addLast(30);

        // Test removeFirst
        assertThat(lld1.removeFirst()).isEqualTo(10);
        assertThat(lld1.toList()).containsExactly(20, 30).inOrder();

        // Test removeLast
        assertThat(lld1.removeLast()).isEqualTo(30);
        assertThat(lld1.toList()).containsExactly(20).inOrder();

        // Test remove the last element
        assertThat(lld1.removeFirst()).isEqualTo(20);
        assertThat(lld1.toList()).isEmpty();

        // Test empty deque
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.removeLast()).isNull();
    }
}