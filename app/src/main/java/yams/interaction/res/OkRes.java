package yams.interaction.res;

public class OkRes implements Res {
    @Override
    public String serializeHeader() {
        return "OK";
    }

    @Override
    public String serializeBody() {
        return "";
    }

}
