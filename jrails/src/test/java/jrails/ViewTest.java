package jrails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.*;

public class ViewTest {

    @Test
    public void empty() {
        assertThat(View.empty().toString(), isEmptyString());
    }
    @Test
    public void testDestroy {
        //add 25 copies of one book
        Book b1 = new Book();
        b1.title = "The Secret Garden";
        b1.author = "Frances Hodgson Burnett";
        b1.num_copies = 25;
        assert(b1.id() == 0);
        b1.save(); 
        assert(b1.id() != 0);
        //add 5 copies of another book
        Book b2 = new Book();
        b2.title = "The Amateurs";
        b2.author = "Sara Shepard";
        b2.num_copies = 5;
        assert(b2.id() == 0);
        b2.save(); 
        assert(b2.id() != 0);
        //create book list
        List<Book> bookList = Model.all(Book.class);
        assert(bookList.size() == 2);

        //destroy book 1
        b1.destroy();
        assert(bookList.size() == 1);
        assert(!bookList.contains(b1));
    }
}