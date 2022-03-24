package yams.interaction.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import yams.interaction.res.ErrRes;

public class ArgumentParser {
    private Map<String, String> map = new HashMap<>();

    public ArgumentParser(String[] args) throws ErrRes.BadReqFormat {
        for (int i = 0; i < args.length; i++) {
            String[] parts = args[i].split(":");
            if (parts.length == 2)
                this.map.put(parts[0], parts[1]);
        }
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(map.get(key));
    }

    public String getRequired(String key) throws ErrRes.BadReqFormat {
        String str = map.get(key);
        if (str == null)
            throw new ErrRes.BadReqFormat();
        return str;
    }
}
