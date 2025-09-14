package com.jpd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/*Module (
    module_id INT PRIMARY KEY,
    course_id INT,
    module_type_id INT,
    is_special BOOLEAN,
    FOREIGN KEY (course_id) REFERENCES Course(course_id),
    FOREIGN KEY (module_type_id) REFERENCES ModuleType(id)
)
*/

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Module {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;

@ManyToOne
@JoinColumn(name = "moduletype_id")
@JsonBackReference
private ModuleType moduleType;
private String titleOfModule;
private boolean isSpecial;
@OneToMany(mappedBy ="module" ,cascade = CascadeType.ALL)
@JsonManagedReference
private List<FlashCard>flashCards;
@OneToMany(mappedBy ="module",cascade = CascadeType.ALL )
@JsonManagedReference
private List<MultipleChoiceQuestion>mutipleChoiceQuestions;
@OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
@JsonManagedReference
private List<GapfillQuestion>gapfillQuestions;
@OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
@JsonManagedReference
private List<ListionChoiceQuestion>listionChoiceQuestions;
@OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
@JsonManagedReference
private List<Passage>passages;
@OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
@JsonManagedReference
private List<SpeakingPictureQuestion>speakingPicturequestion;
@OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
@JsonManagedReference
private List<SpeakingPassageQuestion>speakingPassafeQuestion;
@OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
@JsonManagedReference
private List<CustomerFinishedModule>customerFinishedModules;
@OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
@JsonManagedReference
private List<PdfDocument>pdfs;
@OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
@JsonManagedReference
private List<WritingQuestion>writingQuestions;
@OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
@JsonManagedReference
private List<TeachingVideo>teachingVideos;
}
