using Microsoft.EntityFrameworkCore.Migrations;

namespace UDT.Repository.Migrations
{
    public partial class Added_Year_And_Subtitle : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Subtitle",
                table: "Tasks",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Year",
                table: "Subjects",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Subtitle",
                table: "Tasks");

            migrationBuilder.DropColumn(
                name: "Year",
                table: "Subjects");
        }
    }
}
