package objectprotocol;


public class GetProbeRequest implements Request {

    private String pls;

    public GetProbeRequest(String pls) {
        this.pls = pls;
    }

    public String getPls() {
        return pls;
    }
}

