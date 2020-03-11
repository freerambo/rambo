package workspace;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StreamTest {

    public static void main(String[] args) throws IOException {
        List<TranTypeBO> tranTypeBOList = new ArrayList<>();
        TranTypeBO typeBO = new TranTypeBO();
        typeBO.setLastCurrCd("USD");
        typeBO.setLastTranAmt("100");
        typeBO.setTranCnt(2);
        typeBO.setLastTranTS("2018-11-12 12:23:32");
        typeBO.setTranTyp("CC");
        typeBO.setLastTranAmtUSD("123.6");

        tranTypeBOList.add(typeBO);

        TranTypeBO typeBO1 = new TranTypeBO();
        typeBO1.setLastCurrCd("USD");
        typeBO1.setLastTranAmt("100");
        typeBO1.setTranCnt(2);
        typeBO1.setLastTranTS("2018-01-12 12:23:32");
        typeBO1.setTranTyp("CC");
        typeBO1.setLastTranAmtUSD("123.6");

        tranTypeBOList.add(typeBO1);

       /* System.out.println(JsonMapper.nonDefaultMapper().toJson(tranTypeBOList));
        String jsonString = "[{\"tranTyp\":\"OT\",\"tranCnt\":3,\"lastTranAmt\":15.99,\"lastTranAmtUSD\":15.99,\"lastTranTS\":\"28-AUG-18 01.55.01.00000000 PM\",\"lastCurrCd\":\"USD\"},{\"tranTyp\":\"RR\",\"tranCnt\":2,\"lastTranAmt\":16.99,\"lastTranAmtUSD\":16.99,\"lastTranTS\":\"02-FEB-19 12.07.26.00000000 AM\",\"lastCurrCd\":\"USD\"},{\"tranTyp\":\"BP\",\"tranCnt\":1,\"lastTranAmt\":16.99,\"lastTranAmtUSD\":16.99,\"lastTranTS\":\"28-FEB-19 03.43.04.00000000 PM\",\"lastCurrCd\":\"USD\"}]\n";


        ObjectMapper objectMapper = new ObjectMapper();

        tranTypeBOList = objectMapper.readValue(
                jsonString,
                objectMapper.getTypeFactory().constructCollectionType(
                        List.class, TranTypeBO.class));*/


        TranTypeBO latestTranTypeBO =
                tranTypeBOList.stream().reduce((tranType1, tranType2) -> {
                    TranTypeBO latest = tranType1.getLastTranTS().compareTo(tranType2.getLastTranTS()) >= 0 ? tranType1 : tranType2;
                    latest.setTranCnt(tranType1.getTranCnt() + tranType2.getTranCnt());
                    return latest;
                }).get();

        // set back to merchant
        System.out.println(latestTranTypeBO);
    }
}

