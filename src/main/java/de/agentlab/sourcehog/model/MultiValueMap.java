package de.agentlab.sourcehog.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiValueMap<S, T> {

    private Map<S, List<T>> data = new HashMap<S, List<T>>();

    public void put(S key, T value) {
        List<T> values = data.get(key);
        if (values == null) {
            values = new ArrayList<T>();
            data.put(key, values);
        }
        values.add(value);
    }

    public Set<S> keySet() {
        return this.data.keySet();
    }

    public Collection<T> get(S key) {
        return this.data.get(key);
    }
}
