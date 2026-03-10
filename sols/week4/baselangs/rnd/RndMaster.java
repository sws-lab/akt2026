package week4.baselangs.rnd;

import week4.baselangs.rnd.ast.*;

import java.util.*;

public class RndMaster {
    // Nüüd tagastada kõik võimalikud tulemusväärtused hulgana.
    public static Set<Integer> evalNondeterministic(RndNode node) {
        return switch (node) {
            case RndNum(int value) -> Collections.singleton(value);
            case RndNeg(RndNode neg) -> {
                Set<Integer> result = new HashSet<>();
                for (Integer v : evalNondeterministic(neg)) {
                    result.add(-v);
                }
                yield result;
                //yield evalNondeterministic(neg).stream().map(x -> -x).collect(Collectors.toSet());
            }
            case RndAdd(RndNode left, RndNode right) -> {
                Set<Integer> result = new HashSet<>();
                for (Integer l : evalNondeterministic(left)) {
                    for (Integer r : evalNondeterministic(right)) {
                        result.add(l + r);
                    }
                }
                yield result;
                //yield evalNondeterministic(left).stream().flatMap(x -> evalNondeterministic(right).stream().map(y -> x + y)).collect(Collectors.toSet());
            }
            case RndFlip(RndNode left, RndNode right) -> {
                Set<Integer> result = new HashSet<>();
                result.addAll(evalNondeterministic(left));
                result.addAll(evalNondeterministic(right));
                yield result;
            }
        };
    }

    // Tagastada avaldise võimalike tulemuste tõenäosusjaotus.
    public static Map<Integer, Double> evalDistribution(RndNode node) {
        return switch (node) {
            case RndNum(int num) -> Map.of(num, 1.0);
            case RndNeg(RndNode neg) -> {
                Map<Integer, Double> map = new HashMap<>();
                for (Map.Entry<Integer, Double> entry : evalDistribution(neg).entrySet()) {
                    map.put(-entry.getKey(), entry.getValue());
                }
                yield map;
            }
            case RndAdd(RndNode left, RndNode right) -> {
                Map<Integer, Double> map = new HashMap<>();
                for (Map.Entry<Integer, Double> l : evalDistribution(left).entrySet()) {
                    for (Map.Entry<Integer, Double> r : evalDistribution(right).entrySet()) {
                        // Merge, sest sama tulemuse korral tuleb liita tõenäosused.
                        map.merge(
                                l.getKey() + r.getKey(),
                                l.getValue() * r.getValue(),
                                Double::sum
                        );
                    }
                }
                yield map;
            }
            case RndFlip(RndNode left, RndNode right) -> {
                Map<Integer, Double> map = new HashMap<>();
                for (Map.Entry<Integer, Double> l : evalDistribution(left).entrySet()) {
                    map.put(l.getKey(), 0.5 * l.getValue());
                }
                for (Map.Entry<Integer, Double> r : evalDistribution(right).entrySet()) {
                    // Merge, sest sama tulemuse korral tuleb liita tõenäosused.
                    map.merge(r.getKey(), 0.5 * r.getValue(), Double::sum);
                }
                yield map;
            }
        };
    }
}
