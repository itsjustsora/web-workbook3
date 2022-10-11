package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type; // t, c, w, tc, tw, twc

    private String keyword;

    private String link;

    public String[] getTypes() {
        if (type == null || type.isEmpty()) {
            return null;
        }
        return type.split("");
    }

    public Pageable getPageable(String...props) {
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());
    }

    public String getLink() {
        if (link == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("page="+this.page);
            sb.append("&size="+this.size);

            if (type != null && type.length() > 0) {
                sb.append("&type="+type);
            }

            if (keyword != null) {
                sb.append("&keyword="+ URLEncoder.encode(keyword, StandardCharsets.UTF_8));

                link = sb.toString();
            }
        }
        return link;
    }

}
