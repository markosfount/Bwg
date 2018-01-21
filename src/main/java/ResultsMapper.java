import model.WgResult;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class ResultsMapper {

    public List<WgResult> mapResults(List<Element> elements) {
        return elements.stream()
                .map(this::mapResult)
                .collect(Collectors.toList());
    }

    private WgResult mapResult(Element element) {

        return WgResult.builder().build();
    }

}
