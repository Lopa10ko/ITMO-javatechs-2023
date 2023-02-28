package ru.lopa10ko.banks.Entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Data
public class DepositRules {
    @Setter(AccessLevel.NONE)
    private List<DepositRule> depositRules;
    @Setter(AccessLevel.NONE)
    private BasicValueAmount maxPercent;
    @Setter(AccessLevel.NONE)
    private static DepositRulesBuilder builder;

    private DepositRules(List<DepositRule> rules) {
        maxPercent = new BasicValueAmount(rules.stream()
                .max(Comparator.comparing(DepositRule::getPercent, BasicValueAmount::compareTo))
                .get().getPercent()
                .getValue());
        depositRules = rules.stream()
                .sorted(Comparator.comparing(DepositRule::getCriticalValue, BasicValueAmount::compareTo))
                .toList();
    }

    public static DepositRulesBuilder getBuilder() {return new DepositRulesBuilder();}

    public static class DepositRulesBuilder {
        private final List<DepositRule> rules = new ArrayList<>();
        public DepositRules build() {
            return new DepositRules(rules);
        }

        public DepositRulesBuilder addDepositRule(DepositRule rule) {
            DepositRule finalRule = rule;
            if (rules.contains(rule))
                return this;
            if (rules.stream().anyMatch(r -> r.isOverlapping(finalRule))) {
                DepositRule overlappingRule = rules.stream().filter(r -> r.isOverlapping(finalRule)).findFirst().orElse(null);
                rule = rule.getMaxPercent(Objects.requireNonNull(overlappingRule));
            }

            rules.add(rule);
            return this;
        }
    }
}
