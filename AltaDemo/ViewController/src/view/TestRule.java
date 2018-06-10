package view;

import net.sourceforge.pmd.lang.xml.ast.XmlNode;
import net.sourceforge.pmd.lang.xml.rule.AbstractXmlRule;

public class TestRule extends AbstractXmlRule{
    public Object visit(XmlNode node, Object data) {
            System.out.println("hello world");
            addViolation(data, node);
            return data;
        }
}
