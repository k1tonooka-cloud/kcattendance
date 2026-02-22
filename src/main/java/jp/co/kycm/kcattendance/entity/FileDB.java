package jp.co.kycm.kcattendance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "files")
@Getter @Setter @NoArgsConstructor
public class FileDB {
	  @Id
	  //@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private String id;
	  private String name;
	  private String type;

	  //@Lob // ポイント2: @Lobと@Typeを以下のようにつける(@Lobはサイズが大きいデータのカラムにつけるみたい。@Typeがないと「bigintのデータが出力されてますよ」的なエラーが出る
	  //@Type(type = "org.hibernate.type.BinaryType")
	  //@Column(name = "data")
	  private byte[] data;
	  public FileDB(String name, String type, byte[] data) {
		    this.name = name;
		    this.type = type;
		    this.data = data;
	  }

}
