/* Generated By:JJTree: Do not edit this line. ASTVarInitAsgn.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTVarInitAsgn extends SimpleNode {
  public ASTVarInitAsgn(int id) {
    super(id);
  }

  public ASTVarInitAsgn(CLang p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CLangVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=342c945fdb5ec6c1b043453e6431806d (do not edit this line) */