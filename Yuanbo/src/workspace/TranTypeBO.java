package workspace;


import java.io.Serializable;

/**
 * Created by yuzhu on 9/5/19.
 */
public class TranTypeBO implements Serializable {

    private String tranTyp;
    private Integer tranCnt;
    private String lastTranAmt;
    private String lastTranAmtUSD;
    private String lastTranTS;
    private String lastCurrCd;

    public String getTranTyp() {
        return tranTyp;
    }

    public void setTranTyp(String tranTyp) {
        this.tranTyp = tranTyp;
    }

    public Integer getTranCnt() {
        return tranCnt;
    }

    public void setTranCnt(Integer tranCnt) {
        this.tranCnt = tranCnt;
    }

    public String getLastTranAmt() {
        return lastTranAmt;
    }

    public void setLastTranAmt(String lastTranAmt) {
        this.lastTranAmt = lastTranAmt;
    }

    public String getLastTranAmtUSD() {
        return lastTranAmtUSD;
    }

    public void setLastTranAmtUSD(String lastTranAmtUSD) {
        this.lastTranAmtUSD = lastTranAmtUSD;
    }

    public String getLastTranTS() {
        return lastTranTS;
    }

    public void setLastTranTS(String lastTranTS) {
        this.lastTranTS = lastTranTS;
    }

    public String getLastCurrCd() {
        return lastCurrCd;
    }

    public void setLastCurrCd(String lastCurrCd) {
        this.lastCurrCd = lastCurrCd;
    }

    @Override
    public String toString() {
        return "TranTypeBO{" +
                "tranTyp='" + tranTyp + '\'' +
                ", tranCnt=" + tranCnt +
                ", lastTranAmt='" + lastTranAmt + '\'' +
                ", lastTranAmtUSD='" + lastTranAmtUSD + '\'' +
                ", lastTranTS='" + lastTranTS + '\'' +
                ", lastCurrCd='" + lastCurrCd + '\'' +
                '}';
    }
}