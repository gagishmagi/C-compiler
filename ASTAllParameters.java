/* Generated By:JJTree: Do not edit this line. ASTAllParameters.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTAllParameters extends SimpleNode {
  public ASTAllParameters(int id) {
    super(id);
  }

  public ASTAllParameters(CLang p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CLangVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=afaf936ec4c913576095806a3b9cc4c1 (do not edit this line) */
