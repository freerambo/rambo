/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * blockchian -> Block.java
 * Created on 19 Nov 2017-10:07:38 pm
 */
package blockchian;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  19 Nov 2017 10:07:38 pm
 */
import java.util.Arrays;


public class Block {

    private int previousHash;
    private String[] transactions;

    private int blockHash;

    public Block(int previousHash, String[] transactions) {
        this.previousHash = previousHash;
        this.transactions = transactions;

        Object[] contens = {Arrays.hashCode(transactions), previousHash};
        this.blockHash = Arrays.hashCode(contens);

    }

    public int getPreviousHash() {
        return previousHash;
    }

    public String[] getTransaction() {
        return transactions;
    }

    public int getBlockHash() {
        return blockHash;
    }
}