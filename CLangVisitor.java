/* Generated By:JavaCC: Do not edit this line. CLangVisitor.java Version 7.0.5 */
public interface CLangVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTStart node, Object data);
  public Object visit(ASTCheckSyntax node, Object data);
  public Object visit(ASTAllParameters node, Object data);
  public Object visit(ASTParameter node, Object data);
  public Object visit(ASTFunc node, Object data);
  public Object visit(ASTVarDef node, Object data);
  public Object visit(ASTVarAsgn node, Object data);
  public Object visit(ASTVarInitAsgn node, Object data);
  public Object visit(ASTIfStmt node, Object data);
  public Object visit(ASTWhileStmt node, Object data);
  public Object visit(ASTForStmt node, Object data);
  public Object visit(ASTReturnStmt node, Object data);
  public Object visit(ASTStmt node, Object data);
  public Object visit(ASTStmtBlock node, Object data);
  public Object visit(ASTFuncCall node, Object data);
  public Object visit(ASTExpress node, Object data);
  public Object visit(ASTAssignExpress node, Object data);
  public Object visit(ASTBoolExpress node, Object data);
  public Object visit(ASTBoolExpressOr node, Object data);
  public Object visit(ASTBoolExpressAnd node, Object data);
  public Object visit(ASTBoolExpressEq node, Object data);
  public Object visit(ASTBoolCompOper node, Object data);
  public Object visit(ASTBoolExpressComp node, Object data);
  public Object visit(ASTAddExpress node, Object data);
  public Object visit(ASTMultExpress node, Object data);
  public Object visit(ASTUnaExpress node, Object data);
  public Object visit(ASTConstExpress node, Object data);
}
/* JavaCC - OriginalChecksum=1997e0ef9c586d3934524bb2e89c7821 (do not edit this line) */
