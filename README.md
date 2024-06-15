# Araç Kiralama Uygulaması
Bu proje, Java ve Swing kullanılarak geliştirilen bir araç kiralama uygulamasıdır.

## Özellikler
- Araç kaydı ve plaka bilgilerinin tutulması
- Belirli kriterlere göre uygun araçların listelenmesi
- Rezervasyon işlemlerinin yapılması

## Kullanım
- Uygulama çalıştırıldığında, Swing arayüzü üzerinden araçları kaydedebilir, uygun araçları listeleyebilir ve rezervasyon yapabilirsiniz.

- ## Proje Yapısı
Proje genel olarak dört ana modülden oluşur: `entity`, `dao`, `business` ve `view`. Bu modüller projenin farklı katmanlarını temsil eder ve her biri belirli bir görevi yerine getirir.

- **Entity Modülü**
  - Veritabanı tablolarını ve bu tablolara ait varlık nesnelerini tanımlar.
  - Kullanıcı, marka, model, araç, rezervasyon gibi temel entity nesnelerini içerir.
  - Bu nesneler arasındaki ilişkileri belirler.

- **DAO (Veri Erişim Nesnesi) Modülü**
  - Veritabanı erişimi ve işlemleri için bir arayüz sağlar.
  - Entity modülündeki nesnelerin veritabanına kaydedilmesi, güncellenmesi ve silinmesi süreçlerini yönetir.
  - Veritabanından veri alma işlemlerini gerçekleştirir.

- **Business Modülü**
  - İş mantığını yönetir ve uygulama içindeki temel işlemleri gerçekleştirir.
  - Fiyatlandırma ve araç kiralama hesaplamaları gibi iş mantığı operasyonlarını yönetir.
  - Veritabanı işlemleri için DAO modülü ile etkileşime girer.

- **View Modülü**
  - Kullanıcı arayüzünü (UI) yönetir ve kullanıcıyla etkileşimi sağlar.
  - Araç listesi ve kiralama ekranı gibi kullanıcı bilgilerini gösterir.
  - Kullanıcının girdiği bilgileri iş katmanına ileterek işlemleri başlatır.

