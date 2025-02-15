public class AccountDatabase {
    private Account [] accounts;
    private int size;
    private Archive archive; //a linked list of closed account
    private int find(Account account) {} //return the index or -1 not found.
    private void grow() {} //increase the array capacity by 4
    public boolean contains(Account account) {} //check before add/remove
    public void add(Account account) {} //add to end of array
    public void remove(Account account) {}//replace it with the last item
    public boolean withdraw(AccountNumber number, double amount) {}
    public void deposit(AccountNumber number, double amount) {}
    public void printArchive() //print closed accounts
    public void printByBranch() {}
    public void printByHolder() {}
    public void printByType() {}
}