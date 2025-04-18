package test.java.ru.tasks.cache;

import main.java.ru.tasks.cache.Account;
import main.java.ru.tasks.cache.AccountStorage;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AccountStorageTest {

    @Test
    public void whenAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.getAmount(), is(100));
    }

    @Test
    public void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.getAmount(), is(200));
    }

    @Test
    public void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        Assert.assertFalse(storage.getById(1).isPresent());
    }

    @Test
    public void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        Assert.assertEquals(firstAccount.getAmount(), 0);
        Assert.assertEquals(secondAccount.getAmount(), 200);
    }

    @Test
    public void whenBalanceLessThenException() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 1));
        storage.add(new Account(2, 2));

        Assert.assertThrows(IllegalStateException.class, () -> storage.transfer(1, 2, 100));
    }

    @Test
    public void transferWhenIdNotExist() {
        AccountStorage storage = new AccountStorage();
        storage.add(new Account(1, 100));

        Assert.assertThrows(IllegalArgumentException.class, () -> storage.transfer(1, 2, 100));
    }
}
