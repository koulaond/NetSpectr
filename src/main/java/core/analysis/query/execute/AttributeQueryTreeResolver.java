package core.analysis.query.execute;

import core.analysis.query.syntax.*;
import org.jsoup.nodes.Attribute;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static core.analysis.query.syntax.LogicalStatementType.AND;
import static core.analysis.query.syntax.LogicalStatementType.OR;

public class AttributeQueryTreeResolver {

    public boolean resolveAttribute(Attribute attribute, Statement statement) {
        AttributeStatementResolver<Statement> attributeStatementResolver = AttributeStatementResolverProvider.getResolver(statement);
        return attributeStatementResolver.resolveAttributeByStatement(attribute, statement);
    }

    private interface AttributeStatementResolver<S extends Statement> {

        boolean resolveAttributeByStatement(Attribute attribute, S statement);
    }

    private static class AttributeLogicalStatementResolver implements AttributeStatementResolver<LogicalStatement<Statement>> {

        @Override
        public boolean resolveAttributeByStatement(Attribute attribute, LogicalStatement<Statement> statement) {
            Set<Statement> subStatements = statement.getSubordinates();
            LogicalStatementType type = statement.getType();
            for (Statement subStatement : subStatements) {
                AttributeStatementResolver resolver = AttributeStatementResolverProvider.getResolver(subStatement);
                boolean result = resolver.resolveAttributeByStatement(attribute, subStatement);
                if (AND.equals(type) && !result) {
                    return false;
                } else if (OR.equals(type) && result) {
                    return true;
                }
            }
            // On this place, type is either AND and all results were true, or type is OR and no result was true
            return AND.equals(type);
        }
    }

    private static class AttributeOperableStatementResolver implements AttributeStatementResolver<OperableStatement> {

        @Override
        public boolean resolveAttributeByStatement(Attribute attribute, OperableStatement statement) {
            StatementTarget target = statement.getTarget();
            Operator operator = statement.getOperator();
            boolean result = false;
            switch (target) {
                case ATTRIBUTE_NAME:
                    result = operator.operate(attribute.getKey());
                    break;
                case ATTRIBUTE_VALUE:
                    result = operator.operate(attribute.getValue());
                    break;
            }
            return result;
        }
    }

    private static class AttributeStatementResolverProvider {
        private static final Map<Class<? extends Statement>, AttributeStatementResolver> statementResolvers = new HashMap<>();

        static {
            statementResolvers.put(LogicalStatement.class, new AttributeLogicalStatementResolver());
            statementResolvers.put(OperableStatement.class, new AttributeOperableStatementResolver());
            statementResolvers.put(AttributeQueryTemplate.class, new AttributeLogicalStatementResolver());
        }

        static AttributeStatementResolver getResolver(Statement statement) {
            return statementResolvers.get(statement.getClass());
        }
    }
}
