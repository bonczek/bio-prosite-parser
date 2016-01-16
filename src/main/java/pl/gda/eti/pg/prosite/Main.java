package pl.gda.eti.pg.prosite;

import pl.gda.eti.pg.prosite.pattern.PatternFinder;
import pl.gda.eti.pg.prosite.pattern.PatternParser;
import pl.gda.eti.pg.prosite.rule.Rule;
import pl.gda.eti.pg.prosite.rule.RuleBuilder;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
            switch (args.length) {
                case (0):
                    System.out.println("Nie podałeś żadnego parametru: pierwszy parametr do wzorzec, a drugi to sekwencja do przeszukania");
                    return;
                case (1):
                    System.out.println("Podałeś tylko pierwszy parametr, wymagane są dwa: pierwszy parametr do wzorzec, a drugi to sekwencja do przeszukania");
                    return;
                default:
                    System.out.println("Podałeś za dużo parametrów, wymagane są dwa: pierwszy parametr do wzorzec, a drugi to sekwencja do przeszukania");
                    return;
            }
        }
        String pattern = args[0];
        String sequence = args[1];

        RuleBuilder ruleBuilder = createRulesFromStringPattern(pattern);
        PatternFinder patternFinder = new PatternFinder(ruleBuilder, sequence);
        List<String> reportList = patternFinder.findPattern();
        reportList.forEach(System.out::println);
    }

    private static RuleBuilder createRulesFromStringPattern(String pattern) {
        PatternParser patternParser = new PatternParser();
        List<Rule> rulesChain = patternParser.parse(pattern);
        return new RuleBuilder(rulesChain);
    }

}
