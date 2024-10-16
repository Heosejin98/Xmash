import { RankingDto } from "@/shared/api/ranking";
import { Input, Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/shared/ui";
import { useQuery } from "@tanstack/react-query";
import {
  ColumnDef,
  ColumnFiltersState,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  SortingState,
  useReactTable,
} from "@tanstack/react-table";
import { ChangeEvent, useState } from "react";
import { RankingQueries } from "./ranking.queries";
import { Route } from "@/pages/_layout/ranking";
import { useNavigate } from "@tanstack/react-router";
import { RankingTypeTabs } from "./rankingType.tabs.ui";
import { MatchType } from "@/shared/api/game";
import { Gem } from "lucide-react";

const columns: ColumnDef<RankingDto>[] = [
  {
    accessorKey: "rank",
    header: "순위",
  },
  {
    accessorKey: "userName",
    header: "이름",
  },
  {
    accessorKey: "tier",
    header: "티어",
  },
  {
    accessorKey: "lp",
    header: "LP",
  },
];

export function LeaderBoardList() {
  const { username, matchType } = Route.useSearch();

  const navigate = useNavigate({ from: Route.fullPath });

  const [columnFilters, setColumnFilters] = useState<ColumnFiltersState>([
    { id: "userName", value: username ?? "" },
  ]);
  const [sorting, setSorting] = useState<SortingState>(() => []);
  const { data } = useQuery(RankingQueries.rankingQuery(matchType));

  const setMatchType = (matchType: MatchType) => {
    navigate({ search: { matchType, username } });
  };

  const table = useReactTable({
    data,
    columns,
    onSortingChange: setSorting,
    getCoreRowModel: getCoreRowModel(),
    onColumnFiltersChange: setColumnFilters,
    getFilteredRowModel: getFilteredRowModel(),
    state: {
      sorting,
      columnFilters,
    },
  });

  const onSearch = (e: ChangeEvent<HTMLInputElement>) => {
    table.getColumn("userName")?.setFilterValue(e.target.value);
    navigate({ search: (prev) => ({ ...prev, username: e.target.value }) });
  };

  return (
    <div className="w-full p-3">
      <div className="flex flex-col items-center py-4 gap-2 sm:flex-row">
        <Input
          placeholder="검색할 이름..."
          value={(table.getColumn("userName")?.getFilterValue() as string) ?? ""}
          onChange={onSearch}
          className="max-w-sm"
        />
        <RankingTypeTabs onChange={setMatchType} type={matchType} />
      </div>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => {
                  return (
                    <TableHead key={header.id}>
                      {header.isPlaceholder
                        ? null
                        : flexRender(header.column.columnDef.header, header.getContext())}
                    </TableHead>
                  );
                })}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row) => (
                <TableRow key={row.id} data-state={row.getIsSelected() && "selected"}>
                  {row.getVisibleCells().map((cell) => {
                    if (cell.column.id === "rank" && cell.getValue() === 1) {
                      return (
                        <TableCell key={cell.id}>
                          <Gem></Gem>
                        </TableCell>
                      );
                    }
                    return (
                      <TableCell key={cell.id}>
                        {flexRender(cell.column.columnDef.cell, cell.getContext())}
                      </TableCell>
                    );
                  })}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={columns.length} className="h-24 text-center">
                  No results.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
