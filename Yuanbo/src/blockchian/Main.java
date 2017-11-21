/*
 * Copyright: Energy Research Institute @ NTU
 * Yuanbo
 * blockchian -> Main.java
 * Created on 19 Nov 2017-10:07:11 pm
 */
package blockchian;

/**
 * function description：
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">Rambo Zhu  </a>
 * @version v 1.0 
 * Create:  19 Nov 2017 10:07:11 pm
 */
public class Main {

    /**
		BlockChain at its core 
        Hash = digital signature
        Each block will have:
            List of transactions
            Previous Hash
            Hash
     */

    public static void main(String[] args) {

        String[] genesisTransactions = {"Anuj Sent Kashyap 10 BITCOINS","Kashyap sent 20 BITCOINS to Anuj"};
        Block genesisBlock = new Block(0, genesisTransactions);

        String[] block2Transactions = {"Anuj Sent Kashyap 20 BITCOINS","Kashyap sent 50 BITCOINS to Anuj"};
        Block block2 = new Block(genesisBlock.getBlockHash(), block2Transactions);

        String[] block3Transactions = {"Anuj sent 50 BITCOINS to Elon"};
        Block block3 = new Block(block2.getBlockHash(), block3Transactions);

        System.out.println("Hash of genesis block:");
        System.out.println(genesisBlock.getBlockHash());

        System.out.println("Hash of block 2:");
        System.out.println(block2.getBlockHash());

        System.out.println("Hash of block 3:");
        System.out.println(block3.getBlockHash());

    }


}