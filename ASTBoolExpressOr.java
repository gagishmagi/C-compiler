/* Generated By:JJTree: Do not edit this line. ASTBoolExpressOr.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTBoolExpressOr extends SimpleNode {
  public ASTBoolExpressOr(int id) {
    super(id);
  }

  public ASTBoolExpressOr(CLang p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CLangVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=48cac32fc29b818778d8cf54870abd84 (do not edit this line) */
