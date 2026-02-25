package week3.buildingblocks;

import java.util.*;

public record Transition(int source, String label, int target) {

    public static Map<Integer, Map<String, Integer>> makeTransitionMap(Set<Transition> transitions) {
        Map<Integer, Map<String, Integer>> map = new HashMap<>();
        // Siin on peamine mure, et mapi sees on omakorda vaja neid mapikesed luua!
        for (Transition transition : transitions) {
            int from = transition.source();
            Map<String, Integer> innerMap = map.get(from);
            if (innerMap == null) {  // IntelliJ väidab, et saab paremini... proovige!
                innerMap = new HashMap<>();
                map.put(from, innerMap);
            }
            innerMap.put(transition.label(), transition.target());
        }
        return map;
    }

    static void main() {
        Set<Transition> transitions = new HashSet<>();
        transitions.add(new Transition(1, "a", 2));
        transitions.add(new Transition(1, "b", 3));
        transitions.add(new Transition(2, "c", 3));
        System.out.println(makeTransitionMap(transitions)); // {1={a=2, b=3}, 2={c=3}}
    }

    @Override
    public String toString() {
        return "(%d,%s,%d)".formatted(source, label, target);
    }
}
