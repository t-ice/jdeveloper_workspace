package oracle.jdeveloper.extsamples.auditrefactor;

import java.net.URL;

import java.util.List;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.model.Node;
import oracle.ide.model.NodeFactory;

import oracle.javatools.parser.java.v2.model.SourceMethod;
import oracle.javatools.parser.java.v2.model.SourceVariable;

import oracle.jdeveloper.refactoring.java.RefactoringJavaManager;

public class FixMethodNameCommand extends Command {
    private final Context context;
    private final SourceMethod method;
    
    public FixMethodNameCommand(String name, Context context, SourceMethod method) {
        super(Ide.findOrCreateCmdID(name), Command.MULTI_NODE, name);
        this.context = new Context();
        this.context.setWorkspace(context.getWorkspace());
        this.context.setProject(context.getProject());
        this.method = method;
        URL url = method.getOwningSourceFile().getURL();
        Node node = NodeFactory.find(url);
        this.context.setNode(node);
        setContext(this.context);
    }
    
    public int doit() {
        String oldName = method.getName();
        String newName = MethodNames.PREFIX + oldName;
        String className = method.getOwningClass().getQualifiedName();

        RefactoringJavaManager rm = RefactoringJavaManager.getRefactoringManager();

        List parameters = method.getSourceParameters();
        String[] prmStr = new String[parameters.size()];
        int i = 0;
        for (Object obj : parameters) {
            SourceVariable sv = (SourceVariable)obj;

            String val =
                sv.getSourceType().getResolvedType().getQualifiedName();

            prmStr[i++] = val;
        }
        rm.renameMethod(context, className, oldName, newName, prmStr, false, false, true);

        return OK;
    }
    
    public int undo() {
        return OK;
    }
    
    public Node[] getAffectedNodes() {
        return new Node[] { context.getNode() };
    }
}
