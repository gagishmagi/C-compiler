/* Generated By:JJTree: Do not edit this line. ASTForStmt.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTForStmt extends SimpleNode {
  public ASTForStmt(int id) {
    super(id);
  }

  public ASTForStmt(CLang p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CLangVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=c1eca98cfa8adf86bb0893f3d8ffa565 (do not edit this line) */
