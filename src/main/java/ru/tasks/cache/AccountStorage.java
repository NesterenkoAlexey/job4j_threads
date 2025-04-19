package main.java.ru.tasks.cache;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.getId(), account) == null;
        }
    }

    public synchronized boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.getId(), account) != null;
        }
    }

    public synchronized void delete(int id) {
        synchronized (accounts) {
            accounts.remove(id);
        }
    }

    public synchronized Optional<Account> getById(int id) {
        Optional<Account> account;
        synchronized (accounts) {
            account = Optional.ofNullable(accounts.getOrDefault(id, null));
        }
        return account;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            Account accountFrom = getById(fromId).orElseThrow(
                    () -> new IllegalArgumentException("Счет с id " + fromId + " не найден"));
            Account accountTo = getById(toId).orElseThrow(
                    () -> new IllegalArgumentException("Счет с id " + toId + " не найден"));

            if (accountFrom.getAmount() < amount) {
                throw new IllegalStateException("Баланс меньше нуля");
            }

            accounts.put(fromId, new Account(fromId, accountFrom.getAmount() - amount));
            accounts.put(toId, new Account(toId, accountTo.getAmount() + amount));
        }
    }
}
