package word;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Searching {
	public List<Score> search(String sp) {
		List<String> keyWords = new ArrayList<String>();
	    int k = 2;  //窗口大小/2
	    float d = 0.85f;
	    /**
	     * 标点符号、无用词
	     */
	    Set<String> stopWordSet = new HashSet<String>();
	    stopWordSet.add("是");
	    stopWordSet.add("的");
	    stopWordSet.add("地");
	    stopWordSet.add("从");
	    stopWordSet.add("将");
	    stopWordSet.add("但");
	    stopWordSet.add("都");
	    stopWordSet.add("和");
	    stopWordSet.add("为");
	    stopWordSet.add("让");
	    stopWordSet.add("在");
	    stopWordSet.add("由");
	    stopWordSet.add("上");
	    //读取文件
	    
	    String field=sp;
	   


	    Analyzer analyzer = new IKAnalyzer(true);
	    TokenStream ts = null;
	    //分词
	    try {
	        ts = analyzer.tokenStream("myfield", new StringReader(field));
	        OffsetAttribute offset = (OffsetAttribute) ts.addAttribute(OffsetAttribute.class);
	        CharTermAttribute term = (CharTermAttribute) ts.addAttribute(CharTermAttribute.class);
	        TypeAttribute type = (TypeAttribute) ts.addAttribute(TypeAttribute.class);
	        ts.reset();

	        while (ts.incrementToken()) {
	            if (!stopWordSet.contains(term.toString())) {
	                keyWords.add(term.toString());
	            }
	        }
	        ts.end();
	    } catch (IOException var14) {
	        var14.printStackTrace();
	    } finally {
	        if (ts != null) {
	            try {
	                ts.close();
	            } catch (IOException var13) {
	                var13.printStackTrace();
	            }
	        }

	    }

	    Map<String, Set<String>> relationWords = new HashMap<String, Set<String>>();


	    //获取每个关键词 前后k个的组合
	    for (int i = 0; i < keyWords.size(); i++) {
	        String keyword = keyWords.get(i);
	        Set<String> keySets = relationWords.get(keyword);
	        if (keySets == null) {
	            keySets = new HashSet<String>();
	            relationWords.put(keyword, keySets);
	        }

	        for (int j = i - k; j <= i + k; j++) {
	            if (j < 0 || j >= keyWords.size() || j == i) {
	                continue;
	            } else {
	                keySets.add(keyWords.get(j));
	            }
	        }
	    }

	    for (String s : relationWords.keySet()) {
	        System.out.print(s+" ");
	        for (String s1 : relationWords.get(s)) {
	            System.out.print(s1+" ");
	        }
	        System.out.println();
	    }


	    Map<String, Float> score = new HashMap<String, Float>();
	    float min_diff = 0.1f; //差值最小
	    int max_iter = 100;//最大迭代次数

	    //迭代
	    for (int i = 0; i < max_iter; i++) {
	        Map<String, Float> m = new HashMap<String, Float>();
	        float max_diff = 0;
	        for (String key : relationWords.keySet()) {
	            Set<String> value = relationWords.get(key);
	            //先给每个关键词一个默认rank值
	            m.put(key, 1 - d);
	            //一个关键词的TextRank由其它成员投票出来
	            for (String other : value) {
	                int size = relationWords.get(other).size();
	                if (key.equals(other) || size == 0) {
	                    continue;
	                } else {
	                    m.put(key, m.get(key) + d / size * (score.get(other) == null ? 0 : score.get(other)));
	                }
	            }
//	            System.out.println("m.get(key):"+m.get(key)+" score:"+(score.get(key) == null ? 0 : score.get(key)));
	            max_diff = Math.max(max_diff, Math.abs(m.get(key) - (score.get(key) == null ? 0 : score.get(key))));
	        }

	        score = m;
	        if (max_diff <= min_diff) {
	            System.out.println("迭代次数：" + i);
	            break;
	        }
	    }

	    List<Score> scores = new ArrayList<Score>();
	    for (String s : score.keySet()) {
	        Score score1 = new Score();
	        score1.key = s; 
	        score1.significance = score.get(s);
	        scores.add(score1);
	    }

	    scores.sort(new Comparator<Score>() {
	        public int compare(Score o1, Score o2) {
	            if (o2.significance - o1.significance > 0) {
	                return 1;
	            } else {
	                return -1;
	            }

	        }
	    });
	    
	    return scores;
		
	}
	
}