/* Generated By:JJTree: Do not edit this line. ASTVarDef.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTVarDef extends SimpleNode {
  public ASTVarDef(int id) {
    super(id);
  }

  public ASTVarDef(CLang p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CLangVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=9e3adca4365212e2cb80437a71e20fd3 (do not edit this line) */