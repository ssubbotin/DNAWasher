import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.FASTAFileReader;
import net.sf.jfasta.impl.FASTAElementIterator;
import net.sf.jfasta.impl.FASTAFileReaderImpl;

public class Washer {
    final static int minLen = 110;
    final static int maxLen = 200;

    private static final String MSG_PROGRESS = "VocaTree after %d iterations (of %d): %s";

    static List<FASTAElement> fasta = new ArrayList<FASTAElement>();
    static VocaTree tree = null;

    public final static void loadFasta(String path) {
        try {
            final File file = new File(path);
            FASTAFileReader reader = new FASTAFileReaderImpl(file);
            final FASTAElementIterator it = reader.getIterator();
            while (it.hasNext()) {
                FASTAElement el = it.next();
                int len = el.getSequenceLength(); 
                if((len >= minLen) && (len <= maxLen)) {
                    fasta.add(el);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // load data array
        loadFasta("data/hg19-XmaI_lib_1000_bs_both.fa");
        loadFasta("data/hg19-XmaI_lib_1000_cgi_bs_both.fa");
        System.out.println(fasta.size());

        // build voca tree
        int pos = 0;
        for(FASTAElement el: fasta) {
            VocaTree elTree = new VocaTree(el.getSequence());
            if(tree == null) {
                tree = elTree;
            } else {
                tree.adjust(elTree);
            }
            System.out.println(String.format(MSG_PROGRESS, ++pos, fasta.size(), tree.toString()));
        }
    }    
}
