# JPA Practices


## ch01. Basic Concepts & Configuration
#### 01. Persistence Unit & Persistence Manager
#### 02. EntityManagerFactory
#### 03. EntityManager & Persistence Context
#### 04. EntityTransaction & JpaTransactionManager


## ch02. Mapping I: Entity Mapping
#### 01. 엔티티 클래스와 테이블 (@Entity, @Table)
#### 02. 기본키 (@Id, @GenerateValue)
#### 03. 필드와 컬럼 (@Column, @Enumerated, @Temporal, @Lob, @Type, @Trasient)


## ch03. Persistence Context
#### 01. 영속성 콘텍스트
#### 02. 영속성 관리와 엔티티 생명주기
#### 03. 엔티티 조회, 등록, 수정 그리고 삭제


## ch04. Mapping II: Association Mapping & Writing Repository
#### 01. 단일(One) - 방명록
#### 02. 다대일(ManyToOne)  : 단방향(Unidirectional) : 게시판 [Board -> User]
#### 03. 다대일(ManyToOne)  : 양방향(Bidirectional)  : 온라인북몰 [Order <-> User]
#### 04. 일대다(OneToMany)  : 단방향(Unidirectional) : 게시판 [Board -> Comment]
#### 05. 일대다(OneToMany)  : 양방향(Bidirectional)  : 온라인북몰 [User <-> Order]
#### 06. 일대일(OneToOne)   : 단방향(Unidirectional) : 주테이블 외래키 : JBlog [User -> Blog]
#### 07. 일대일(OneToOne)   : 양방향(Bidirectional)  : 주테이블 외래키 : JBlog [User <-> Blog]
#### 08. 일대일(OneToOne)   : 양방향(Bidirectional)  : 대상테이블 외래키(식별관계) : JBlog [User <-> Blog]
#### 09. 다대다(ManyToMany) : 단방향(Unidirectional) : 음반검색 [Song -> Genre]
#### 10. 다대다(ManyToMany) : 양방향(Bidirectional)  : 음반검색 [Song <-> Genre]
#### 11. 다대다(ManyToMany) : 혼합모델(연결엔티티, 복합키(PK), 식별관계) : @EmbeddedId : 온라인북몰 [User <-> CartItem -> Book]
#### 12. 다대다(ManyToMany) : 혼합모델(연결엔티티, 복합키(PK), 식별관계) : @IdClass : 온라인북몰 [User <-> CartItem -> Book]
#### 13. 다대다(ManyToMany) : 혼합모델(연결엔티티, 새PK, 비식별관계) : 온라인북몰 [User <-> CartItem -> Book]