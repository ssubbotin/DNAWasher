import java.util.ArrayList;
import java.util.List;


public class VocaTree {
    private String root;
    private int hits = 1;
    private List<VocaTree> leafs = new ArrayList<VocaTree>();
    
    public VocaTree(String sequence) {
      root = sequence.substring(0, 1);
      String[] arr = sequence.split(root);
      
      for(String leaf: arr) {
         if(!leaf.isEmpty()) {
             VocaTree newbie = new VocaTree(leaf);
             joinLeaf(newbie);
         }
      }
    }
    
    private void joinLeaf(VocaTree newbie) {
        String newbieRoot = newbie.getRoot();
        
        for(VocaTree leaf: leafs) {
            if(leaf.getRoot().equals(newbieRoot)) {
                leaf.hits++;
                leaf.adjust(newbie);
                return;
            }
        }
        
        leafs.add(newbie);  
    }

    public void adjust(VocaTree tree) {
        if(tree.getRoot().equals(root)) {
            for(VocaTree leaf: tree.leafs) {
                joinLeaf(leaf);
            }
        } else {
            System.err.println("Error: can not adjust to a tree with another root\n");
        }
    }
    
    public String getRoot() {
        return root;
    }
    
    @Override
    public String toString() {
        String res = String.format("%s(%d)", root, hits);
        if (!leafs.isEmpty()) {
            res = res + "->[";
            for(VocaTree leaf: leafs) {
                if(!res.endsWith("[")) {
                    res = res + ",";
                }
                res = res + leaf.toString();
            }
            res = res + "]";
        }
        return res;
    }

    public static void main(String[] args) {
        VocaTree tree = new VocaTree("abraaakadabrt");
        System.out.println(tree.toString());
    }
}
