import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Vector;

public class SymbolTableVisitor extends CLangDefaultVisitor {

    HashMap<String, SymbolTableEntry> functions = new HashMap<String, SymbolTableEntry>();
    Vector<HashMap<String, SymbolTableEntry>> symbols = new Vector<HashMap<String, SymbolTableEntry>>();

    Vector<String> _data;
    Vector<String> _text;

    int stackIndex = 0;
    int index = 0;


    public static class SymbolTableEntry {
        public String name;
        public String type;
        public int offset;

        public SymbolTableEntry(String name, String type, int offset) {
            this.name = name;
            this.type = type;
            this.offset = offset;
        }
    }


    public SymbolTableVisitor() {
        HashMap<String, SymbolTableEntry> s = new HashMap<>();
        this.symbols.add(s);
        this._text = new Vector<>();
        this._data = new Vector<>();

    }

    public SymbolTableEntry findSymbol(String s) {
        for (int i = this.index; i >= 0; --i) {
            SymbolTableEntry tmp = this.symbols.get(i).get(s);

            if (tmp != null)
                return tmp;
        }
        return null;
    }

    public void changeASymbol(SymbolTableEntry s) {
        this.symbols.get(index).put(s.name, s);
    }

    public SymbolTableEntry findAFunc(String s) {
        SymbolTableEntry st = this.functions.get(s);
        if(st != null)
            return st;
        return null;
    }

    public void changeAFunction(SymbolTableEntry s) {
        this.functions.put(s.name, s);
    }

    @Override
    public Object visit(ASTvarDefine node, Object data) {
        if (findSymbol(node.firstToken.next.image) != null) {
            System.err.println(String.format("ERROR: VAR %s REDEFINITION AT %d : %d", node.firstToken.next.image,
            node.firstToken.next.beginLine, node.firstToken.next.beginColumn));
            System.exit(-1);
        }
        System.out.println("Abstact Search Tree VarDefine node data is = "+node.children[0]);
        System.out.println("Abstact Search Tree VarDefine has num of chidren= "+node.children.length);

        boolean isInt = node.firstToken.image.equals("int");
        if (isInt)
            this.stackIndex+=4;
        else
            this.stackIndex++;

        SymbolTableEntry e = new SymbolTableEntry(node.firstToken.next.image, node.firstToken.image,this.stackIndex);
        System.out.println("Abstact Search Tree VarDefine first child data= "+data);
        //asembly
        //data = super.visit(node, data);
        if (node.children.length > 0)
        {
            System.out.println("Abstact Search Tree VarDefine first child offset= "+e.offset);
            data = node.children[0].jjtAccept(this, data);
           // System.out.println("ASTvarDefine 2 data= "+data);
            System.out.println("Abstact Search Tree VarDefine  second child offset= "+e.offset);
            _text.add("pop rax");
            _text.add(String.format("mov %s [rbp - %d], %s", isInt ? "dword" : "byte", e.offset, isInt ? "eax" : "al"));
        }

        changeASymbol(e);
        return data;
        //return super.visit(node, data);
    }


    @Override
    public Object visit(ASTaddExpression node, Object data) {
        // ADD EXPRESION of 2 numbers
        data = node.children[0].jjtAccept(this, data);
        if (node.children.length > 1)
        {
            data = node.children[1].jjtAccept(this, data);
            _text.add("pop rbx");
            _text.add("pop rax");
            _text.add("add rax, rbx");
            _text.add("push rax");
        }
        return data;
    }

    @Override
    public Object visit(ASTassignExpression node, Object data) {
        if (findSymbol(node.firstToken.image) == null) {
            System.err.println(String.format("ASSING ERROR: VAR %s ISNT DEFINED AT %d : %d", node.firstToken.image,
                    node.firstToken.next.beginLine, node.firstToken.next.beginColumn));
            System.exit(-1);
        }
        return super.visit(node,data);
    }

    @Override
    public Object visit(ASTconstExpression node, Object data) {
        if (node.firstToken.kind == CLang.ID) {
            SymbolTableEntry e = findSymbol(node.firstToken.image);
            SymbolTableEntry var =findSymbol(node.firstToken.next.next.image);
            if ( e == null) {
                System.err.println(String.format("Variable %s is not defined at %d : %d", node.firstToken.image,
                        node.firstToken.beginLine, node.firstToken.beginColumn));
                System.exit(-1);
            }

            System.out.println("Abstact Search Tree constExpression e = " + e);
            System.out.println("Abstact Search Tree constExpression var = " + var);

            //looks for and integer statement
            boolean isInt = e.type.equals("int");

            _text.add(String.format("mov %s, %s [rbp - %d]", isInt ? "eax" : "al", isInt ? "dword" : "byte", e.offset));
            _text.add("push rax");
        }
        System.out.println("Abstact Search Tree constExpression no if");



        return super.visit(node, data);
    }


    @Override
    public Object visit(ASTunaryExpression node, Object data) {
        // unaray expression if/else style
        return super.visit(node, data);
    }

    @Override
    public Object visit(ASTfunction node, Object data) {
        System.out.println("Abstact Search Tree function");
        if (findSymbol(node.firstToken.next.image) != null) {
            System.err.println(String.format("ERROR: variable %s is not allowed redifination will be at %d : %d", node.firstToken.next.image,
                    node.firstToken.next.beginLine, node.firstToken.next.beginColumn));
            System.exit(-1);
        }
        HashMap<String, SymbolTableEntry> s = new HashMap<>();
        this.symbols.add(s);
        System.out.println("node.firstToken.image = " + node.firstToken.image);
        System.out.println("node.firstToken.next.image = " + node.firstToken.next.image);
        changeAFunction(new SymbolTableEntry(node.firstToken.next.image, node.firstToken.image,this.stackIndex));
        changeAFunction(new SymbolTableEntry(node.firstToken.next.image, node.firstToken.image,this.stackIndex));
        ++this.index;

        _text.add(String.format("%s:" ,node.firstToken.next.image));
        _text.add("push rbp");
        _text.add("mov rbp, rsp");
        Object c = super.visit(node, data);
        _text.add("mov rsp, rbp");
        _text.add("pop rbp");
        _text.add("ret");

        this.index--;
        return c;
    }

    @Override
    public Object visit(ASTfunctionCall node, Object data)
    {
        System.out.println("Abstact Search Tree functionCall");
        if (findAFunc(node.firstToken.image) == null) {
            System.err.println(String.format("ERROR: Function %s NOT DEFINED AT %d : %d", node.firstToken.image,
                    node.firstToken.beginLine, node.firstToken.beginColumn));
            System.exit(-1);
        }
        return super.visit(node,data);
    }



    @Override
    public Object visit(ASTparam node, Object data) {
        System.out.println("Abstact Search Tree paramaters");
        Object res = super.visit(node, data);

        SymbolTableEntry e = new SymbolTableEntry(node.firstToken.next.image, node.firstToken.image, this.stackIndex);
        this.stackIndex += 4;
        changeASymbol(e);

        return res;
    }


    @Override
    public Object visit(ASTStatementBlock node, Object data) {
        ++this.index;
        System.out.println("Abstact Search Tree StatementBlock");
        HashMap<String, SymbolTableEntry> s = new HashMap<>();
        this.symbols.add(s);

        Object c = super.visit(node, data);

        this.symbols.remove(this.index);
        this.index--;

        return c;
    }

    @Override
    public Object visit(ASTStatement node, Object data) {
        System.out.println("Abstact Search Tree Statement= "+node.firstToken.image);

        boolean isIf = node.firstToken.image.equals("if");
        boolean isFor = node.firstToken.image.equals("for");
        boolean isWhile = node.firstToken.image.equals("while");
        boolean isReturn = node.firstToken.image.equals("return");

        if(isIf){ //not fin
            _text.add("mov eax, $x");
            _text.add("cmp eax, 0");
            _text.add("jne end");
            _text.add(" mov eax, 1");
            _text.add("end:");
        }

        else if(isFor || isWhile) //not fin
        {
            _text.add("mov eax, $x");
            _text.add("cmp eax, 0x0A");
            _text.add("jg end");
            _text.add("beginning:");
            _text.add("inc eax");
            _text.add("cmp eax, 0x0A");
            _text.add("jle beginning");
            _text.add("end:");
        }

        else if(isReturn){ // not fin
            _text.add("mov rsp, rbp");
            _text.add("pop rbp");
            _text.add("ret");
        }



        Object c = super.visit(node, data);
        return c;
    }

    @Override
    public Object visit(ASTStart node, Object data) {
        System.out.println("Main program");
        Object o = super.visit(node, data);
        //
        System.out.println("SECTION .TEXT\n" + "GLOBAL main\n" + "\n" + "printChar:\n" + "    push rbp\n"
                + "    mov rbp, rsp\n" + "    push rdi\n" + "    mov byte [rbp - 5], 0x41\n"
                + "    mov byte [rbp - 4], 0x53\n" + "    mov byte [rbp - 3], 0x41\n" + "    mov byte [rbp - 2], 0x46\n"
                + "    mov byte [rbp - 1], 0\n" + "    mov rax, 1\n" + "    mov rdi, 1\n" + "    lea rsi, [rbp -5]\n"
                + "    mov rdx, 5\n" + "    syscall \n" + "\n" + "    mov rsp, rbp\n" + "    pop rbp\n" + "    ret\n"
                + "\n" + "printNumber:\n" + "    push rbp\n" + "    mov rbp, rsp\n" + "    mov rsi, rdi\n"
                + "    lea rdi, [rbp - 1]\n" + "    mov byte [rdi], 0\n" + "    mov rax, rsi\n" + "    while:\n"
                + "    cmp rax, 0\n" + "    je done\n" + "    mov rcx, 10\n" + "    mov rdx, 0\n" + "    div rcx\n"
                + "    dec rdi\n" + "    add dl, 0x30\n" + "    mov byte [rdi], dl\n" + "    jmp while\n" + "\n"
                + "    done:\n" + "        mov rax, 1\n" + "        lea rsi, [rdi]\n" + "        mov rdx, rsp\n"
                + "        sub rdx, rsi\n" + "        mov rdi, 1\n" + "        syscall \n" + "\n"
                + "        mov rsp, rbp\n" + "        pop rbp\n" + "        ret\n" + "\n" + "readInteger:\n"
                + "    push rbp\n" + "    mov rbp, rsp\n" + "\n" + "    mov rdx, 10\n" + "    mov qword [rbp - 10], 0\n"
                + "    mov word [rbp - 2], 0\n" + "    lea rsi, [rbp - 10]\n" + "    mov rdi, 0 ; stdin\n"
                + "    mov rax, 0 ; sys_read\n" + "    syscall\n" + "\n" + "    xor rax, rax\n" + "    xor rbx, rbx\n"
                + "    lea rcx, [rbp - 10]\n" + "    \n" + "    copy_byte:\n" + "        cmp rbx, 10\n"
                + "        je read_done    \n" + "        mov dl, byte [rcx]\n" + "        cmp dl, 10\n"
                + "        jle read_done\n" + "        sub rdx, 0x30\n" + "        imul rax, 10\n"
                + "        add rax, rdx\n" + "        nextchar:\n" + "            inc rcx\n" + "            inc rbx\n"
                + "            jmp copy_byte\n" + "    read_done:\n" + "        mov rsp, rbp\n" + "        pop rbp\n"
                + "        ret\n" + "\n");
        for (String s : _text)
            System.out.println(s);
        return o;
    }
    public static void main(String[] args) throws FileNotFoundException, ParseException {

        new CLang(new FileInputStream(args[0]));

        CLang.Start();

        CLang.jjtree.rootNode().jjtAccept(new SymbolTableVisitor(), null);
    }
}
